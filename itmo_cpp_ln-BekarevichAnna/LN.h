#pragma once
#include "vector.h"
#include <string_view>

class LN
{
  public:
	LN(Vector&& v);
	LN(long long n = 0);
	LN(const char* s);
	LN(const LN& v);
	LN(LN&& v);
	LN(std::string_view& v);

	LN& operator=(const LN& v);
	LN& operator=(LN&& v);

	~LN();

	inline bool operator==(const LN& other) const { return any_isNan(other) && comparison_operators(other) == 0; }
	inline bool operator<(const LN& other) const { return any_isNan(other) && comparison_operators(other) == -1; }
	inline bool operator>(const LN& other) const { return any_isNan(other) && comparison_operators(other) == 1; }
	inline bool operator<=(const LN& other) const { return any_isNan(other) && !(*this > other); }
	inline bool operator>=(const LN& other) const { return any_isNan(other) && !(*this < other); }
	inline bool operator!=(const LN& other) const { return any_isNan(other) && !(*this == other); }

	inline LN operator-() const
	{
		if (m_isNan)
		{
			return *this;
		}
		LN result = *this;
		if (!is_zero())
			result.m_isNegative = !result.m_isNegative;
		return result;
	}

	LN operator+(const LN& other) const;
	LN& operator+=(const LN& other);
	LN operator-(const LN& other) const;
	LN& operator-=(const LN& other);
	LN operator*(const LN& other) const;
	LN& operator*=(const LN& other);
	LN operator/(const LN& other) const;
	LN& operator/=(const LN& other);
	LN operator%(const LN& other) const;
	LN& operator%=(const LN& other);
	LN operator~() const;
	LN operator*(long long v) const;
	LN& operator*=(long long v);
	LN operator/(long long v) const;
	long long operator%(long long v) const;

	explicit operator long long() const { return static_cast< long long >(get(1)) * m_block_size + get(0); }

	explicit operator bool() const { return !is_zero(); }

	friend std::ostream& operator<<(std::ostream& stream, LN val);

	int sign() const;
	LN abs() const;

  private:
	Vector m_number;
	const int m_block_size = 1e9;
	bool m_isNegative;
	bool m_isNan;

	size_t length() const;
	bool is_zero() const;
	bool any_isNan(const LN& other) const;
	LN bin_search(const LN& b) const;

	int comparison_operators(const LN& other) const;
	int comparison_abs(const LN& other) const;

	void shorten();
	int get(int pos) const;
	void add(const int value);
	void set(int pos, int value);

	void add_operators(const LN& other, int signa, int signb);
	void div_mod(const LN& other, LN& div, LN& mod) const;
};
LN operator""_ln(const char* args);