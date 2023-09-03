#pragma once

#include <stdbool.h>
#include <stddef.h>

struct Matrix
{
	size_t n, m;
	size_t real_n, real_m;
	double *data;
	double *begin;
	bool transposed;
};

struct Matrix Matrix(double *data, double *begin, size_t n, size_t m, size_t rn, size_t rm);

struct Matrix new_matrix(double *data, size_t n, size_t m);

double get_matrix(struct Matrix a, size_t i, size_t j);

struct Matrix new_matrix_mem(size_t n, size_t m);

void set_matrix(struct Matrix a, size_t i, size_t j, double val);

struct Matrix submatrix(struct Matrix m, size_t sx, size_t sy, size_t lx, size_t ly);

struct Matrix copy_matrix(struct Matrix m);

void print_matrix(struct Matrix a);

void zero(struct Matrix a);

void subtract_matrix(struct Matrix a, struct Matrix b, struct Matrix c);

void free_matrix(struct Matrix a);

double scalar_product(struct Matrix a, struct Matrix b);

void add_matrix_scalar(struct Matrix a, double val);

void mul_matrix_scalar(struct Matrix a, double val);

void transpose_matrix(struct Matrix *a);

double euclidean_norm_matrix(struct Matrix a);