#include "Matrix.h"

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Matrix Matrix(double *data, double *begin, size_t n, size_t m, size_t rn, size_t rm)
{
	struct Matrix ans;
	ans.n = n;
	ans.m = m;
	ans.real_n = rn;
	ans.real_m = rm;
	ans.data = data;
	ans.begin = begin;
	ans.transposed = false;
	return ans;
}

struct Matrix new_matrix(double *data, size_t n, size_t m)
{
	return Matrix(data, data, n, m, n, m);
}

double get_matrix(struct Matrix a, size_t i, size_t j)
{
	if (!a.transposed)
	{
		return a.begin[i * a.real_m + j];
	}
	else
	{
		return a.begin[j * a.real_m + i];
	}
}

struct Matrix new_matrix_mem(size_t n, size_t m)
{
	double *data = malloc(sizeof(double) * n * m);
	if (!data)
	{
		return new_matrix(NULL, 0, 0);
	}
	return new_matrix(data, n, m);
}

void set_matrix(struct Matrix a, size_t i, size_t j, double val)
{
	if (!a.transposed)
	{
		a.begin[i * a.real_m + j] = val;
	}
	else
	{
		a.begin[j * a.real_m + i] = val;
	}
}

struct Matrix submatrix(struct Matrix m, size_t sx, size_t sy, size_t lx, size_t ly)
{
	struct Matrix ans = m;
	ans.n = lx;
	ans.m = ly;
	ans.begin = ans.begin + sy + sx * ans.real_m;
	return ans;
}

struct Matrix copy_matrix(struct Matrix m)
{
	double *data = malloc(sizeof(double) * m.n * m.m);	  // ай-ай-ай
	if (!data)
	{
		return new_matrix(NULL, m.n, m.m);
	}
	if (!m.transposed)
	{
		for (size_t i = 0; i < m.n; i++)
		{
			memcpy(data + i * m.m, m.begin + i * m.real_m, m.m * sizeof(double));
		}
		return new_matrix(data, m.n, m.m);
	}
	else
	{
		for (size_t i = 0; i < m.m; i++)
		{
			memcpy(data + i * m.n, m.begin + i * m.real_m, m.n * sizeof(double));
		}
		struct Matrix ans = Matrix(data, data, m.n, m.m, m.m, m.n);
		ans.transposed = m.transposed;
		return ans;
	}
}
void print_matrix(struct Matrix a)
{
	for (size_t i = 0; i < a.n; i++)
	{
		for (size_t j = 0; j < a.m; j++)
		{
			printf("%lf ", get_matrix(a, i, j));
		}
		printf("\n");
	}
}

void zero(struct Matrix a)
{
	for (size_t i = 0; i < a.n; i++)
	{
		for (size_t j = 0; j < a.m; j++)
		{
			set_matrix(a, i, j, 0);
		}
	}
}

void subtract_matrix(struct Matrix a, struct Matrix b, struct Matrix c)
{
	for (size_t i = 0; i < a.n; i++)
	{
		for (size_t j = 0; j < a.m; j++)
		{
			set_matrix(c, i, j, get_matrix(a, i, j) - get_matrix(b, i, j));
		}
	}
}

void free_matrix(struct Matrix a)
{
	free(a.data);
}

double scalar_product(struct Matrix a, struct Matrix b)
{
	double ans = 0;
	for (int i = 0; i < a.n; i++)
	{
		for (int g = 0; g < a.m; g++)
		{
			ans += get_matrix(a, i, g) * get_matrix(b, i, g);
		}
	}
	return ans;
}

void add_matrix_scalar(struct Matrix a, double val)
{
	for (int i = 0; i < a.n; i++)
	{
		for (int g = 0; g < a.m; g++)
		{
			set_matrix(a, i, g, get_matrix(a, i, g) + val);
		}
	}
}
void mul_matrix_scalar(struct Matrix a, double val)
{
	for (int i = 0; i < a.n; i++)
	{
		for (int g = 0; g < a.m; g++)
		{
			set_matrix(a, i, g, get_matrix(a, i, g) * val);
		}
	}
}

void transpose_matrix(struct Matrix *a)
{
	(*a).transposed = true;
	int bf;
	bf = (*a).n;
	(*a).n = (*a).m;
	(*a).m = bf;
}

double euclidean_norm_matrix(struct Matrix a)
{
	double res = 0;
	for (int i = 0; i < a.n; i++)
	{
		for (int j = 0; j < a.m; j++)
		{
			res += get_matrix(a, i, j) * get_matrix(a, i, j);
		}
	}
	return sqrt(res);
}
