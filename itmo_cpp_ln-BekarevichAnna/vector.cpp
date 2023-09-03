#include "vector.h"

#include <cstring>
#include <stdexcept>

Vector::Vector() : Vector(1) {}

Vector::Vector(size_t n)
{
	if (n <= 0)
	{
		throw std::invalid_argument("n must be greater than zero");
	}
	m_length = n;
	m_v = (int*)malloc(m_length * sizeof(int));
	if (!m_v)
	{
		throw std::bad_alloc();
	}
	m_back = 0;
}

Vector::Vector(size_t n, int val) : Vector(n)
{
	memset(m_v, val, sizeof(int) * m_length);
	m_back = n;
}

Vector::Vector(const Vector& other)
{
	m_length = other.m_length;
	m_back = other.m_back;
	if (m_v)
	{
		int* temp = (int*)realloc(m_v, m_length * sizeof(int));
		if (!temp)
		{
			throw std::bad_alloc();
		}
		m_v = temp;
	}
	else
	{
		m_v = (int*)malloc(m_length * sizeof(int));
	}
	if (!m_v)
	{
		throw std::bad_alloc();
	}
	memcpy(m_v, other.m_v, other.m_length * sizeof(int));
}
Vector::Vector(Vector&& other)
{
	m_length = other.m_length;
	m_back = other.m_back;
	if (m_v)
		free(m_v);
	m_v = other.m_v;
	other.m_v = nullptr;
}

Vector& Vector::operator=(const Vector& other)
{
	if (this == &other)
	{
		return *this;
	}
	m_length = other.m_length;
	m_back = other.m_back;
	if (m_v)
	{
		int* temp = (int*)realloc(m_v, m_length * sizeof(int));
		if (!temp)
		{
			throw std::bad_alloc();
		}
		m_v = temp;
	}
	else
	{
		m_v = (int*)malloc(m_length * sizeof(int));
	}
	if (!m_v)
	{
		throw std::bad_alloc();
	}
	memcpy(m_v, other.m_v, other.m_length * sizeof(int));
	return *this;
}

Vector& Vector::operator=(Vector&& other)
{
	m_length = other.m_length;
	m_back = other.m_back;
	if (m_v)
		free(m_v);
	m_v = other.m_v;
	other.m_v = nullptr;
	return *this;
}

Vector::~Vector()
{
	if (m_v)
	{
		free(m_v);
	}
}

int Vector::operator[](size_t pos) const
{
	return m_v[pos];
}

void Vector::resize(size_t pos)
{
	if (pos < m_length)
	{
		return;
	}
	size_t was = m_length;
	while (pos >= m_length)
	{
		m_length *= 2;
	}
	int* temp = (int*)realloc(m_v, m_length * sizeof(int));
	if (!temp)
	{
		throw std::bad_alloc();
	}
	m_v = temp;
	memset(m_v + was, 0, sizeof(int) * (m_length - was));
}

void Vector::push_back(int value)
{
	resize(m_back);
	m_v[m_back++] = value;
}
void Vector::pop_back()
{
	m_back--;
}

void Vector::push_front(int value)
{
	reverse();
	push_back(value);
	reverse();
}

void Vector::pop_front()
{
	reverse();
	m_back--;
	reverse();
}

int Vector::back() const
{
	return m_v[m_back - 1];
}

size_t Vector::size() const
{
	return m_back;
}

void Vector::set(int pos, int value)
{
	if (pos >= size())
	{
		resize(pos + 1);
		m_back = pos + 1;
	}
	m_v[pos] = value;
}
bool Vector::empty() const
{
	return m_back == 0;
}

void Vector::reverse()
{
	int temp;
	for (int i = 0; i < size() / 2; i++)
	{
		temp = m_v[i];
		m_v[i] = m_v[size() - i - 1];
		m_v[size() - i - 1] = temp;
	}
}

void Vector::print() const
{
	for (int i = 0; i < size(); i++)
	{
		std::cout << m_v[i] << " ";
	}
	std::cout << '\n';
}

void Vector::clear()
{
	m_back = 0;
}