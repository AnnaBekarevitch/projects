#pragma once
#include <stdbool.h>
#include <stdio.h>

struct Result
{
	double re, im;
	bool complex;
};

struct Result Result(double real, double img, bool c);

void print_result(FILE* out, struct Result a);