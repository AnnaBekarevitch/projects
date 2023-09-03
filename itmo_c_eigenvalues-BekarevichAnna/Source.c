#include "Matrix.h"
#include "Result.h"
#include "return_codes.h"

#include <math.h>
#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

double EPS = 1e-20;

struct Matrix hessenberg_form(struct Matrix a)
{
	for (int i = 0; i < a.n - 2; i++)
	{
		struct Matrix col_vector = submatrix(a, i + 1, i, a.n - i - 1, 1);
		struct Matrix old_col_vector = copy_matrix(col_vector);
		if (!old_col_vector.data)
		{
			return new_matrix(NULL, 0, 0);
		}
		int x = col_vector.begin[0];
		col_vector.begin[0] = euclidean_norm_matrix(col_vector);
		if (x >= 0)
		{
			col_vector.begin[0] = -col_vector.begin[0];
		}
		zero(submatrix(col_vector, 1, 0, col_vector.n - 1, 1));
		struct Matrix norm_matrix = new_matrix_mem(a.n - i - 1, 1);
		if (!norm_matrix.data)
		{
			return new_matrix(NULL, 0, 0);
		}
		subtract_matrix(old_col_vector, col_vector, norm_matrix);
		double norm = euclidean_norm_matrix(norm_matrix);
		if (norm > EPS)
		{
			mul_matrix_scalar(norm_matrix, 1 / norm);
			for (int j = i + 1; j < a.n; j++)
			{
				struct Matrix x = copy_matrix(norm_matrix);
				if (!x.data)
				{
					return new_matrix(NULL, 0, 0);
				}
				mul_matrix_scalar(x, 2 * scalar_product(submatrix(a, i + 1, j, a.n - i - 1, 1), norm_matrix));
				subtract_matrix(submatrix(a, i + 1, j, a.n - i - 1, 1), x, submatrix(a, i + 1, j, a.n - i - 1, 1));
				free_matrix(x);
			}
			transpose_matrix(&norm_matrix);
			for (int j = 0; j < a.n; j++)
			{
				struct Matrix x = copy_matrix(norm_matrix);
				if (!x.data)
				{
					return new_matrix(NULL, 0, 0);
				}
				mul_matrix_scalar(x, 2 * scalar_product(submatrix(a, j, i + 1, 1, a.m - i - 1), norm_matrix));
				subtract_matrix(submatrix(a, j, i + 1, 1, a.m - i - 1), x, submatrix(a, j, i + 1, 1, a.m - i - 1));
				free_matrix(x);
			}
		}
		free_matrix(old_col_vector);
		free_matrix(norm_matrix);
	}
	return a;
}

void get_rotation(struct Matrix a, double* sn, double* cs)
{
	double s = sqrt(get_matrix(a, 0, 0) * get_matrix(a, 0, 0) + get_matrix(a, 1, 0) * get_matrix(a, 1, 0));
	*cs = (s < EPS ? 0 : get_matrix(a, 0, 0) / s);
	*sn = (s < EPS ? 1 : get_matrix(a, 1, 0) / s);
}

void rotation(struct Matrix a, double sn, double cs, int iter)
{
	for (int i = iter; i < a.n; i++)
	{
		double e1 = cs * get_matrix(a, iter, i) + sn * get_matrix(a, iter + 1, i);
		double e2 = -sn * get_matrix(a, iter, i) + cs * get_matrix(a, iter + 1, i);
		set_matrix(a, iter, i, e1);
		set_matrix(a, iter + 1, i, e2);
	}
}

void rotation_transposed(struct Matrix a, double sn, double cs, int iter)
{
	for (int i = 0; i < iter + 2; i++)
	{
		double e1 = cs * get_matrix(a, i, iter) + sn * get_matrix(a, i, iter + 1);
		double e2 = -sn * get_matrix(a, i, iter) + cs * get_matrix(a, i, iter + 1);
		set_matrix(a, i, iter, e1);
		set_matrix(a, i, iter + 1, e2);
	}
}

void solve_complex(double a, double b, double c, struct Result* ev1, struct Result* ev2)
{
	double d = (b * b - 4 * a * c);
	if (d < 0)
	{
		d = sqrt(-d);
		*ev1 = Result((-b) / (2 * a), (-d) / (2 * a), true);
		*ev2 = Result((-b) / (2 * a), d / (2 * a), true);
	}
	else
	{
		d = sqrt(d);
		*ev1 = Result((-b - d) / (2 * a), 0, false);
		*ev2 = Result((-b + d) / (2 * a), 0, false);
	}
}

bool qr_algorithm(struct Matrix a, struct Result* ans)
{
	if (!a.data)
	{
		return false;
	}
	int ans_back = 0;
	double* rotations = malloc(2 * a.n * sizeof(double));
	if (!rotations)
	{
		return false;
	}
	for (int g = 0; g < 10000; g++)
	{
		int rotations_back = 0;
		for (int i = 0; i < a.n - 1; i++)
		{
			double sn, cs;
			get_rotation(submatrix(a, i, i, 2, 1), &sn, &cs);
			rotations[rotations_back++] = sn;
			rotations[rotations_back++] = cs;
			rotation(a, sn, cs, i);
		}
		for (int i = 0; i < a.n - 1; i++)
		{
			rotation_transposed(a, rotations[i * 2], rotations[i * 2 + 1], i);
		}
	}
	for (int i = 0; i < a.n; i++)
	{
		if (i == a.n - 1 || fabs(get_matrix(a, i + 1, i)) < EPS)
		{
			ans[ans_back++] = Result(get_matrix(a, i, i), 0, false);
		}
		else
		{
			struct Result* f = &ans[ans_back++];
			struct Result* s = &ans[ans_back++];
			solve_complex(
				1.0,
				-get_matrix(a, i, i) - get_matrix(a, i + 1, i + 1),
				get_matrix(a, i, i) * get_matrix(a, i + 1, i + 1) - get_matrix(a, i, i + 1) * get_matrix(a, i + 1, i),
				f,
				s);
			i++;
		}
	}
	free(rotations);
	return true;
}

int main(int argc, char** argv)
{
	if (argc != 3)
	{
		fprintf(stderr, "The parameter or number of parameters is incorrect\nExpected: <name1>.txt <name2>.txt");
		return ERROR_PARAMETER_INVALID;
	}
	FILE* in = fopen(argv[1], "r");
	if (!in)
	{
		fprintf(stderr, "File %s did not open\n", argv[1]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	int n;
	fscanf(in, "%d", &n);
	double* data = malloc(n * n * sizeof(double));
	if (!data)
	{
		fclose(in);
		fprintf(stderr, "Failed to allocate memory\n");
		return ERROR_OUT_OF_MEMORY;
	}
	for (size_t i = 0; i < n; i++)
	{
		for (size_t j = 0; j < n; j++)
		{
			fscanf(in, "%lf", &data[i * n + j]);
		}
	}
	fclose(in);
	FILE* out = fopen(argv[2], "w");
	if (!out)
	{
		fprintf(stderr, "File %s did not open\n", argv[2]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	struct Matrix inp_matrix = new_matrix(data, n, n);
	struct Result* result = malloc(n * sizeof(struct Result));
	if (!result)
	{
		fclose(out);
		fprintf(stderr, "Failed to allocate memory\n");
		return ERROR_OUT_OF_MEMORY;
	}
	if (!qr_algorithm(hessenberg_form(inp_matrix), result))
	{
		fclose(out);
		fprintf(stderr, "Failed to allocate memory\n");
		return ERROR_OUT_OF_MEMORY;
	}
	for (size_t i = 0; i < n; i++)
	{
		print_result(out, result[i]);
	}
	fclose(out);
	free(result);
	free_matrix(inp_matrix);
	return SUCCESS;
}
