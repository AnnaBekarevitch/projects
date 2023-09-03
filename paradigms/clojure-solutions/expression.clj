(defn evalute [f] (fn [& x] ( fn [args] (apply f (apply mapv #(% args) x)))))  
(defn divd ([x] (/ 1.0 (double x)))
           ([x & other] (/ (double x) (double (apply * other)))))
(defn add [& x] ((evalute +) x))
(defn subtract [& x] ((evalute -) x))
(defn multiply [& x] ((evalute *) x))
(defn divide [& x] ((evalute divd) x))
(defn negate [& x] ((evalute #(* -1 %)) x))
(defn arcTan [& x] ((evalute #(Math/atan %)) x))
(defn arcTan2 [& x] ((evalute #(Math/atan2 %1 %2)) x))
(def ops1 {'+ add, '- subtract, '* multiply, '/ divide, 
'negate negate, 'atan arcTan, 'atan2 arcTan2 })
(def vars #{'x, 'y, 'z})
(def constant constantly)
(defn variable [x] 
(fn [args] (get args x)))

(defn genParser [ops varCreater constCreater]
    (fn [s]
        (letfn [(parse [val]
                    (cond 
                        (list? val) (apply (get ops (first val)) (mapv parse (rest val)))
                        (number? val) (constCreater val)
                        :else (varCreater (name val))
                    )
                )]
        (parse (read-string s)))
    )
)
(def parseFunction (genParser ops1 variable constant))
;///////////////////////////////////////////////////////////////////////////////////////////////////////////
(load-file "proto.clj")
(declare Variable Constant ZERO ONE Add Subtract Multiply Divide Negate Sinh Cosh)
(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringPostfix (method :toStringPostfix))
(def diff (method :diff))
(def _func (field :func))
(def _df (field :df))
(def _sign (field :sign))
(def _args (field :args))
(def OpProto {
    :evaluate (fn [this vars] (apply (_func this) (mapv (fn [n] (evaluate n vars)) (vec (_args this)))))
    :toString (fn [this] (str "(" (_sign this) " " (clojure.string/join " " (mapv (fn [n] (toString n)) (vec (_args this)))) ")"))
    :toStringPostfix (fn [this] (str "(" (clojure.string/join " " (mapv (fn [n] (toStringPostfix n)) (vec (_args this)))) " " (_sign this) ")"))
    :diff (fn [this var] ((_df this) (_args this) (mapv #(diff % var) (_args this))))
})
(defn Operation [this & args] (assoc this :args args))
(defn MakeOp [func sign df] (constructor Operation (assoc OpProto 
    :df df 
    :func func
    :sign sign
)))
(def ConstProto {
    :evaluate (fn [this data] (_args  this))
    :toString (fn [this] (str (_args  this)))
    :toStringPostfix toString
    :diff (fn [this var] ZERO)
})
(def Constant (constructor (fn [this arg] (assoc this :args arg)) ConstProto))
(def ZERO (Constant 0))
(def ONE (Constant 1))
(def VarProto {
    :evaluate (fn [this var] (get var (clojure.string/lower-case (subs (_args this) 0 1))))
    :toString (fn [this] (_args this))
    :toStringPostfix toString
    :diff (fn [this var] (cond (= (_args this) var) ONE
                         :else ZERO))
})
(def Variable (constructor (fn [this arg] (assoc this :args arg)) VarProto))
(def Add (MakeOp + "+" (fn [args, dargs] (apply Add dargs))))
(def Subtract (MakeOp - "-" (fn [args, dargs] (apply Subtract dargs))))
(def Negate (MakeOp #(* -1 %) "negate" (fn [args, dargs] (apply Negate dargs))))
(def UPow (MakeOp #(Math/pow (Math/E) %) "**" (fn [args, dargs] (apply UPow dargs))))
(def ULog (MakeOp #(Math/log %) "//" (fn [args, dargs] (apply ULog dargs))))
(def Cosh (MakeOp #(Math/cosh %) "cosh" (fn [args, dargs] (apply Multiply dargs (apply Sinh args)))))
(def Sinh (MakeOp #(Math/sinh %) "sinh" (fn [args, dargs] (apply Multiply dargs (apply Cosh args)))))
(defn multDiff ([args, dargs] (Multiply (apply Multiply args) (apply Add (mapv #(apply Divide %) ((partial mapv vector)  dargs args))))))
(def Multiply (MakeOp * "*" multDiff))
(defn divDiff ([args, dargs]
	(if (== (count args) 1)
		(Negate (Divide (first dargs) (Multiply (first args) (first args))))
		(let [x (first args) y (rest args) dx (first dargs) dy (rest dargs)]
			(Divide (Subtract (Multiply dx (apply Multiply y))
												(Multiply (multDiff y dy) x)) (Multiply (apply Multiply y) (apply Multiply y)))))))
(def Divide (MakeOp divd "/" divDiff))
(def opsObject { (symbol "//") ULog, '** UPow, '+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'sinh Sinh, 'cosh Cosh })
(def parseObject (genParser opsObject Variable Constant)) 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(load-file "parser.clj") 
(def *digit (+char "0123456789"))
(defn +ignore-nil [p]
	(+map #(if (nil? %) 'ignore %) p))
(defn sign [s & tail]
	(if (#{\- \+} s)
		(cons s tail)
		tail))
(def *number (+map Constant (+map read-string (+str (+seqf sign (+opt (+char "-+")) (+str (+plus *digit)) (+opt (+char ".")) (+opt (+str (+plus *digit))))))))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def *negate 
	(+seq *ws (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e") *ws))
(def *upow 
	(+seq *ws (+char "*") (+char "*") *ws))
(def *ulog 
	(+seq *ws (+char "/") (+char "/") *ws))
(def *XYZ (+map Variable (+str (+plus (+char "XYZxyz")))))
(def *opSign (+map (comp (fn [n] (get opsObject n)) symbol) 
(+str (+or *negate *ulog *upow (+seq *ws (+char "+-/*") *ws)))))
(defn *seq [begin p end]
	(+seqn 0
		(+ignore (+char begin))
		*ws
		(+opt (+seqf cons p (+star (+seqn 0 *ws p *ws))))
		*ws
		(+ignore (+char end))))
(defn *block [p]
	(+map (fn [n] (apply (last n) (drop-last n))) (*seq "(" p ")")))
(defn *value []
	(+or
		*number
		*XYZ
		*opSign
		(*block (delay (*value)))))
(def parseObjectPostfix (+parser (+seqn 0 *ws (*value) *ws)))
