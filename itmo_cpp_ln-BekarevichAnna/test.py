import os
import binascii
import random
from datetime import datetime
import math
import subprocess


number_length = 100
test_length = 10
test_count = 1000


def gen_hex():
    val = str(binascii.b2a_hex(os.urandom(random.randint(1, number_length))))[2:-1]
    if random.random() < 0.1:
        val = '-' + val
    return val

def compile():
    os.system("g++ *.cpp -o solve.exe")


bins = ['+', '/', '*', '%', '-']
uns = ['~', '_']


def true_div(a, b):
    val = a // b
    if abs(val * b) > abs(a):
        if val < 0:
            val += 1
        else:
            val -= 1
    return val


def solve(data):
    stack = []
    
    for i in data:
        #print(i)
        if i in bins:
            
            a = stack.pop()
            b = stack.pop()
            if a == "nan" or b == 'nan':
                stack.append("nan")
            elif i == "+":
                stack.append(a + b)
            elif i == "/":
                stack.append(true_div(a, b) if b != 0 else "nan")
            elif i == "*":
                stack.append(a * b)
            elif i == "%":
                stack.append((a - true_div(a, b) * b) if b != 0 else "nan")
            else:
                stack.append(a - b)
        elif i in uns:
            a = stack.pop()
            if a == "nan":
                stack.append("nan")
            elif i == "~":
                stack.append(math.isqrt(a) if a >= 0 else "nan")
            else:
                stack.append(-a)
        else:
            stack.append(int(i, 16))
        #print(stack)
    if(stack[-1] == "nan"):
        return "nan"
    val = hex(stack[-1])
    if len(val) and val[0] == '-':
        return '-' + val[3:]
    return val[2:]

if __name__ == '__main__':
    compile()
    random.seed(datetime.now())
    for i in range(1, test_count + 1):
        print('testing - ' + str(i) + "/" + str(test_count))
        res = []
        counter = 0
        for g in range(random.randint(1, test_length)):
            res.append(gen_hex())
            counter += 1
            if counter >= 1 and random.random() < 0.25:
                res.append(random.choice(uns))
            if counter >= 2 and random.random() < 0.5:
                res.append(random.choice(bins))
                counter -= 1
        while counter > 1:
            res.append(random.choice(bins))
            counter -= 1
        o = open('in.txt', 'w', encoding='utf-8')
        o.write("\n".join(map(str, res)))
        o.close()
        subprocess.call(["solve.exe", "in.txt", "out.txt"], shell=False)
        os.system("solve.exe in.txt out.txt")
        i = open('out.txt', 'r', encoding='utf-8')
        data = i.readline().strip()
        i.close()
        check = str(solve(res))
        print('got : ' + data)
        print('expected : ' + check)
        assert data.lower() == check.lower()
    print('SUCCESS')
    print('Test count - ' + str(test_count))
    print('Test length - ' + str(test_length))
    print('Number length - ' + str(number_length))
