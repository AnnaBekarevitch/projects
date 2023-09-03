const OperationPrototype = function(...ops) {
    this.ops = ops
}
OperationPrototype.prototype.evaluate = function(...val) {
    return this.func(...this.ops.map(op => op.evaluate(...val)))
}
OperationPrototype.prototype.diff = function(val) {
    return this.diffOperation(...this.ops, ...this.ops.map(op => op.diff(val)))
}
OperationPrototype.prototype.toString = function() {
    return [...this.ops, this.sign].join(" ")
}
OperationPrototype.prototype.prefix = function() {
	return `(${this.sign} ${[...this.ops.map(x => x.prefix())].join(" ")})`
}
const OpPrototype = function(func, sign, diff) {
    const Op = function(...operands) {
        OperationPrototype.call(this, ...operands)
    };
    Op.prototype = Object.create(OperationPrototype.prototype)
    Op.prototype.func = func        
    Op.prototype.sign = sign
    Op.prototype.diffOperation = diff
    Op.prototype.constructor = Op
	Object.defineProperty(Op, "length", {value: func.length})
    return Op
};
function Const (val) { this.val = val }
Const.prototype.evaluate = function(x, y, z) { return this.val }
Const.prototype.diff = function(x) { return new Const(0) }
Const.prototype.toString = function() { return String(this.val) }
Const.prototype.prefix = function() { return String(this.val) }
function Variable (val) { this.val = val }
Variable.prototype.evaluate = function(x, y, z) { return ({"x" : x, "y" : y, "z" : z}[this.val]) }
Variable.prototype.diff = function(x) { return new Const((x === this.val ? 1 : 0)) }
Variable.prototype.toString = function() { return this.val }
Variable.prototype.prefix = function() { return this.val }
const Add = OpPrototype((a, b) => a + b, '+', (a, b, da, db) => new Add(da, db))
const ArcTan = OpPrototype((a) => Math.atan(a), 'atan', (a, da) => new Multiply(da, new Divide(new Const(1), new Add(new Const(1), new Multiply(a, a)))))
const ArcTan2 = OpPrototype((a, b) => Math.atan2(a, b), 'atan2', (a, b, da, db) => new Multiply(new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b)), new Divide(new Const(1), new Add(new Const(1), new Multiply(new Divide(a, b), new Divide(a, b))))))
const Subtract = OpPrototype((a, b) => a - b, '-', (a, b, da, db) => new Subtract(da, db))
const Multiply = OpPrototype((a, b) => a * b, '*', (a, b, da, db) => new Add(new Multiply(a, db), new Multiply(da, b)))
const Divide = OpPrototype((a, b) => a / b, '/', (a, b, da, db) => new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b))) 
const Negate = OpPrototype((a) => -a, 'negate', (a, da) => new Negate(da))
const Sum = OpPrototype((...val) => val.reduce((a, b) => a += b, 0), "sum", (...args) => {
	let n = args.length / 2
	return new Sum(...args.slice(-n))
})
const Avg = OpPrototype((...val) => val.reduce((a, b) => a += b, 0) / val.length, "avg", (...args) => {
	let n = args.length / 2
	return new Divide(new Sum(...args.slice(-n)), new Const(n))
})
const ops = {"*": Multiply, "+": Add, "-": Subtract, "/": Divide, "negate" : Negate, "sum" : Sum, "avg" : Avg, "atan" : ArcTan, "atan2" : ArcTan2}
const multops = ["sum", "avg"]
const vars = ["x", "y", "z"]
function parser(stack, val){
  if (val in ops){
    stack.push(new ops[val](...stack.splice(stack.length - ops[val].length, ops[val].length)))
  } else if(vars.includes(val)){
    stack.push(new Variable(val))
  } else{
    stack.push(new Const(Number(val)))
  }
  return stack
}
const parse = (expression) => expression.split(" ").filter(n => n.length > 0).reduce(parser, []).pop();
const isWhitespace = function(val){
    return !(val.trim() === val && val.length > 0)
}
const CharSource = function(string) {
	this.source = string + '\0'
	this.pos = 0
}
CharSource.prototype.hasNext = function() { return this.pos < this.source.length }
CharSource.prototype.next = function() { 
	const chr = this.source[this.pos]
	this.pos += 1
	return chr
}
const ParseError = function(message) {
	Error.call(this, message)
}
ParseError.prototype = Object.create(Error.prototype)
const Parser = function(string) {
	this.source =  new CharSource(string)
	this.END = '\0' 
	this.chr = ''	
}
Parser.prototype = Object.create(Parser.prototype)
Parser.prototype.constructor = Parser
Parser.prototype.take = function() {
	const result = this.chr
	this.chr = this.source.hasNext() ? this.source.next() : this.END
	return result
}
Parser.prototype.test = function(expected) { return this.chr === expected }
Parser.prototype.takeExpected = function(expected) {
	if (this.test(expected)) {
		this.take()
		return true
	}
	return false
}
Parser.prototype.expect = function(expected) {
	if (!this.takeExpected(expected)) {
		throw new ParseError(`ParseError: Expected '${expected}', found '${this.chr === this.END ? 'END' : this.chr}'`)
	}
	return true
}
Parser.prototype.expects = function(expected) { expected.split('').forEach(v => this.expect(v)) }
Parser.prototype.eof = function() { return this.takeExpected(END) }
Parser.prototype.between = function(from, to) { return from <= this.chr && this.chr <= to }
Parser.prototype.skipWhiteSpace = function() { 
	while(isWhitespace(this.chr) && this.chr !== this.END) {
		this.take()
	}		
}
Parser.prototype.parseInteger = function(flag) {
	let result = ''
	while (this.between('0', '9')) {
		result += this.chr
		this.take()
	}
	if(result === ''){
		throw new ParseError("Wrong number")
	}
	return new Const(parseInt(result) * flag)  
}
const parseValue = function(parser, sign){
	parser.skipWhiteSpace()
	if (parser.between('0', '9')) {
		return parser.parseInteger(sign)
	}
	if(parser.test('-')){
		parser.expect('-')
		return parser.parseInteger(-1)
	}
	for (const v of vars) {
		let pos = parser.source.pos
		try {
			parser.expects(v)
			return new Variable(v)
		} catch (e) {
			if(parser.source.pos !== pos) {
				throw new ParseError("Unknown token")
			}
		}
	}
	parser.skipWhiteSpace()
	if(parser.test('(')){
		return parseBlock(parser, false)
	}
	return null
}
const parseOp = function(parser){
	parser.skipWhiteSpace()
	for (const key of Object.keys(ops)) {
		let pos = parser.source.pos
		try {
			parser.expects(key)
			return key
		} catch (e) {
			if(parser.source.pos !== pos) {
				throw new ParseError("Unknown token")
			}
		}
	}
	return null
}
const parseBlock = function(parser, root){
	parser.skipWhiteSpace()
	parser.expect('(')
	let op = parseOp(parser)
	if (!root && op === null) throw new ParseError("Expected token")
	if(op === null || (op === "-" && parser.between('0', '9'))){	
		let v = parseValue(parser, op === '-' ? -1 : 1)
		if(v != null){
			parser.skipWhiteSpace()
			parser.expect(')')
			return v
		}
		throw new ParseError("Expected token")
	}
	let arr = []
	if (multops.includes(op)) {
		parser.skipWhiteSpace()
		while (true) {
			let val = parseValue(parser, 1)
			if(val === null){
				break	
			}
			arr.push(val)
		} 
	} else {
		for (let i = 0; i < ops[op].length; i++) {
			let val = parseValue(parser, 1)
			if(val === null){
				throw new ParseError("Not enough args")
			}
			arr.push(val)
		}
	}		
	parser.skipWhiteSpace()
	parser.expect(')')
	return new ops[op](...arr)
}
const parsePrefix = function(string)  {
	const parser = new Parser('(' + string + ')')
	parser.take()
	let x = parseBlock(parser, true)
	parser.skipWhiteSpace()
	if(parser.chr !== parser.END){
		throw new ParseError('ParseError: Expected end of expression, but found additional characters')
	}
	return x
}
