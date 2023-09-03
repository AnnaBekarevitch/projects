#pragma once
#include <iostream>
#include <stdexcept>

class Vector
{
  public:
	Vector();
	Vector(size_t n, int val);
	Vector(size_t n);
	Vector(const Vector& v);
	Vector(Vector&& v);

	Vector& operator=(const Vector& other);
	Vector& operator=(Vector&& other);

	~Vector();

	int operator[](size_t pos) const;

	void reverse();
	void resize(size_t pos);
	void push_back(int value);
	void push_front(int value);
	void pop_back();
	void pop_front();
	int back() const;
	size_t size() const;
	void set(int pos, int value);
	bool empty() const;
	void print() const;
	void clear();

  private:
	int* m_v = nullptr;
	size_t m_length;
	size_t m_back;
};