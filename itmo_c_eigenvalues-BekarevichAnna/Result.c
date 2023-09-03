#include "Result.h"

#include <stdbool.h>
#include <stdio.h>

struct Result Result(double real, double img, bool c)
{
	struct Result ans;
	ans.re = real;
	ans.im = img;
	ans.complex = c;
	return ans;
}

void print_result(FILE* out, struct Result a)
{
	if (a.complex)
	{
		fprintf(out, "%g %c%gi\n", a.re, a.im < 0 ? '-' : '+', a.im < 0 ? -a.im : a.im);
	}
	else
	{
		fprintf(out, "%g\n", a.re);
	}
}