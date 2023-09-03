(defn gen [f] (fn [& x] (apply mapv f x)))
(defn condV [& x] (and (every? vector? x) (apply (fn [& x] (apply = (mapv count x))) x) (every? #(every? number? %) x)))
(defn condM [& x] (and (every? vector? x) (every? #(every? condV %) x)))
(defn condS [& x] (or (every? number? x) (and (every? vector? x) (every? = (mapv count x)) (apply mapv condS x))))
(defn newVector [f x] 
    {:pre [(apply condV x)]
     :post [(condV %)]} 
    (apply (gen f) x))
(defn newMatrix [f x] 
    {:pre [(apply condM x) (apply (fn [& x] (and (every? (fn [& x] (apply = (mapv count x))) x) (apply = (mapv count x)) (apply = (mapv #(count (first %)) x)))) x)]
     :post [(condM %)]} 
    (apply (gen f) x))
(defn v+ [& x] (newVector + x))
(defn v- [& x] (newVector - x))
(defn v* [& x] (newVector * x))
(defn vd [& x] (newVector / x))
(defn m+ [& x] (newMatrix v+ x))
(defn m- [& x] (newMatrix v- x))
(defn m* [& x] (newMatrix v* x))
(defn md [& x] (newMatrix vd x))

(defn genS [f] (fn rec [& x] (if (every? number? x) (apply f x) (apply mapv rec x))))
	

(defn newS [f x]  
    {:pre [(apply condS x)]
     :post [(condS %)]} 
	(apply (genS f) x))

(defn s+ [& x] (newS + x))
(defn s- [& x] (newS - x))
(defn s* [& x] (newS * x))
(defn sd [& x] (newS / x))
(defn v*s [v & x] 
    {:pre [(condV v) (every? number? x)]
     :post [(condV %)]} 
    ((gen #(* % (apply * x))) v))
(defn m*s [m & x] 
    {:pre [(condM m) (every? number? x)]
     :post [(condM %)]}
    ((gen #(v*s % (apply * x))) m))
(defn m*v [m & x] 
    {:pre [(condM m) (every? condV x)]
     :post [(condV %)]} 
    ((gen #(apply + (v* % (apply v* x)))) m))
(defn transpose [m] 
    {:pre [(condM m)]
     :post [(condM %)]}
    (apply mapv vector m)) 
(defn m*m [& x] 
    {:pre [(apply condM x)]
     :post [(condM %)]} 
    (reduce (fn [a, b] (mapv #(m*v (transpose b) %) a)) x))
(defn scalar [& x] 
    {:pre [(apply condV x)]
    :post [(number? %)]} 
    (apply + (apply mapv * x)))
(defn vect [& x] 
    {:pre [(apply condV x) (= (count (first x)) 3)]
     :post [(condV %) (= (count %) 3)]}
    (reduce (fn [v1, v2][(- (* (nth v1 1) (nth v2 2)) (* (nth v2 1) (nth v1 2))) (- (* (nth v2 0) (nth v1 2)) (* (nth v1 0) (nth v2 2))) (- (* (nth v1 0) (nth v2 1)) (* (nth v2 0) (nth v1 1)))]) x))
