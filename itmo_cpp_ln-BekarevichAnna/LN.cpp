#include "LN.h"

#include <string_view>

#include <math.h>
#include <string.h>

#define max(x, y) (((x) < (y)) ? (y) : (x))

LN::LN(long long n)
{
	m_number = Vector(1);
	m_isNan = false;
	if (n < 0)
	{
		m_isNegative = true;
		n = -n;
	}
	else
		m_isNegative = false;
	if (n == 0)
	{
		m_number.push_back(0);
	}
	while (n)
	{
		m_number.push_back(n % m_block_size);
		n /= m_block_size;
	}
}

LN::LN(const char* s)
{
	LN now(0LL);

	int base = 16;
	for (int i = (s[0] == '-'); i < strlen(s); i++)
	{
		now *= base;
		if ('0' <= s[i] && s[i] <= '9')
			now += s[i] - '0';
		else if ('A' <= s[i] && s[i] <= 'F')
			now += s[i] - 'A' + 10;
		else if ('a' <= s[i] && s[i] <= 'f')
			now += s[i] - 'a' + 10;
	}
	if (s[0] == '-')
	{
		now.m_isNegative = true;
	}
	*this = now;
}

LN::LN(std::string_view& s)
{
	long long now = 0;
	m_isNan = false;
	m_isNegative = false;
	m_number = Vector(1);
	if (s[0] == '-')
	{
		m_isNegative = true;
	}
	for (int i = s.size() - 1; i >= (s[0] == '-'); i--)
	{
		now *= 16;
		if ('0' <= s[i] && s[i] <= '9')
			now += s[i] - '0';
		else if ('A' <= s[i] && s[i] <= 'F')
			now += s[i] - 'A' + 10;
		else if ('a' <= s[i] && s[i] <= 'f')
			now += s[i] - 'a' + 10;

		if (now >= m_block_size)
		{
			m_number.push_back(now % m_block_size);
			now /= m_block_size;
		}
	}
	if (now)
	{
		m_number.push_back(now);
	}
}

LN::LN(Vector&& v) : m_isNegative(false), m_isNan(false), m_number(v) {}

LN::LN(const LN& v)
{
	m_isNegative = v.m_isNegative;
	m_isNan = v.m_isNan;
	m_number = v.m_number;
}

LN::LN(LN&& v)
{
	m_isNegative = v.m_isNegative;
	m_isNan = v.m_isNan;
	m_number = std::move(v.m_number);
}

LN& LN::operator=(const LN& v)
{
	if (this != &v)
	{
		m_isNegative = v.m_isNegative;
		m_isNan = v.m_isNan;
		m_number = v.m_number;
	}
	return *this;
}

LN& LN::operator=(LN&& v)
{
	m_isNegative = v.m_isNegative;
	m_isNan = v.m_isNan;
	m_number = std::move(v.m_number);
	return *this;
}

LN::~LN() {}

LN LN::operator+(const LN& other) const
{
	LN result = *this;
	result += other;
	return result;
}

LN& LN::operator+=(const LN& other)
{
	if (m_isNan)
	{
		return *this;
	}
	if (other.m_isNan)
	{
		m_isNan = true;
		return *this;
	}
	if (m_isNegative == other.m_isNegative)
	{
		add_operators(other, 1, 1);
		return *this;
	}
	int c_abs = comparison_abs(other);
	if (c_abs == 0)
	{
		*this = LN(0LL);
		return *this;
	}
	if (c_abs == 1)
	{
		add_operators(other, 1, -1);
		shorten();
		if (is_zero())
		{
			m_isNegative = false;
			;
		}
		return *this;
	}

	add_operators(other, -1, 1);

	shorten();
	m_isNegative = other.m_isNegative;
	if (is_zero())
	{
		m_isNegative = false;
		;
	}
	return *this;
}
LN LN::operator*(long long v) const
{
	if (m_isNan)
	{
		return *this;
	}
	LN ans = *this;
	ans *= v;
	return ans;
}
LN& LN::operator*=(long long v)
{
	if (m_isNan)
	{
		return *this;
	}
	if (llabs(v) >= m_block_size)
	{
		*this = *this * LN(v);
		return *this;
	}
	if (v < 0)
	{
		m_isNegative = !m_isNegative;
		v = -v;
	}
	long long next = 0, now;
	for (size_t i = 0; i < length(); i++)
	{
		now = next + get(i) * v;
		next = now / m_block_size;
		set(i, now % m_block_size);
	}
	if (next)
	{
		add(next);
	}
	shorten();
	if (is_zero())
	{
		m_isNegative = false;
	}
	return *this;
}

LN LN::operator-(const LN& other) const
{
	return *this + -other;
}

LN& LN::operator-=(const LN& other)
{
	*this -= other;
	return *this;
}

LN LN::operator*(const LN& other) const
{
	if (m_isNan)
	{
		return *this;
	}
	if (other.m_isNan)
	{
		return other;
	}
	Vector result(length() + other.length(), 0);
	for (size_t i = 0; i < length(); ++i)
	{
		if (get(i))
		{
			long long x = get(i), now, next = 0;
			for (int j = 0; j < other.length(); ++j)
			{
				now = result[i + j] + x * other.get(j) + next;
				next = now / m_block_size;
				result.set(i + j, now % m_block_size);
			}
			if (next)
			{
				result.set(i + other.length(), next % m_block_size);
			}
		}
	}
	if (result.empty())
	{
		return LN(0LL);
	}
	LN Answer(std::move(result));
	Answer.shorten();
	if (!Answer.is_zero())
	{
		Answer.m_isNegative = (m_isNegative + other.m_isNegative) % 2;
	}
	return Answer;
}
LN LN::operator/(long long v) const
{
	if (m_isNan)
	{
		return *this;
	}
	if (llabs(v) >= m_block_size)
	{
		return *this / LN(v);
	}
	Vector res;
	long long prev = 0;
	for (int i = length() - 1; i >= 0; --i)
	{
		res.push_back((get(i) + prev * m_block_size) / v);
		prev = (get(i) + prev * m_block_size) % v;
	}
	res.reverse();
	LN ans = res.empty() ? LN(0LL) : LN(std::move(res));
	if (!ans.is_zero())
	{
		ans.m_isNegative = (m_isNegative + (v < 0));
	}
	return ans;
}

long long LN::operator%(long long v) const
{
	long long m = 0;
	for (int i = length() - 1; i >= 0; --i)
		m = (get(i) + m * m_block_size) % v;
	return m * sign();
}

LN& LN::operator*=(const LN& other)
{
	*this = *this * other;
	return *this;
}

LN LN::operator/(const LN& other) const
{
	if (m_isNan)
	{
		return *this;
	}
	if (other.m_isNan)
	{
		return other;
	}
	LN dv, md;
	div_mod(other, dv, md);
	return dv;
}

LN LN::operator%(const LN& other) const
{
	if (m_isNan)
	{
		return *this;
	}
	if (other.m_isNan)
	{
		return other;
	}
	LN dv, md;
	div_mod(other, dv, md);
	return md;
}

LN& LN::operator/=(const LN& other)
{
	*this = *this / other;
	return *this;
}

LN& LN::operator%=(const LN& other)
{
	*this = *this % other;
	return *this;
}

LN LN::operator~() const
{
	LN ans(0LL);
	if (m_isNan || m_isNegative)
	{
		ans.m_isNan = true;
		return ans;
	}
	size_t st = 0;
	LN bf(1);
	while (bf * bf * 100LL <= *this)
	{
		bf *= 10LL;
		st += 1;
	}
	while (true)
	{
		while (((ans + bf) * (ans + bf)) <= *this)
		{
			ans += bf;
		}
		bf = bf / 10LL;
		if (st == 0)
		{
			break;
		}
		st -= 1;
	}
	return ans;
}

std::ostream& operator<<(std::ostream& stream, LN val)
{
	if (val.m_isNan)
	{
		stream << "NaN";
		return stream;
	}
	LN x(val);
	bool minus = false;
	if (val.m_isNegative)
	{
		minus = true;
		x = -x;
	}
	Vector ans;
	int mod = 16;
	while (!x.is_zero())
	{
		LN y = x % mod;
		ans.push_back((y.m_number[0] < 10 ? '0' : 'A' - 10) + y.m_number[0]);
		x = x / mod;
		x.shorten();
	}
	if (ans.empty())
	{
		ans.push_back('0');
	}
	if (minus)
	{
		ans.push_back('-');
	}

	ans.reverse();
	for (int i = 0; i < ans.size(); i++)
	{
		stream << static_cast< char >(ans[i]);
	}
	return stream;
}
LN operator""_ln(const char* val)
{
	if (strlen(val) >= 2 && val[0] == '0' && (val[1] == 'x' || val[1] == 'X'))
	{
		return LN(val + 2);
	}
	return LN(val);
}
size_t LN::length() const
{
	return m_number.size();
}

int LN::get(int pos) const
{
	if (pos >= length())
		return 0;
	return m_number[pos];
}

void LN::set(int pos, int value)
{
	m_number.set(pos, value);
}

void LN::add(const int value)
{
	m_number.push_back(value);
}

void LN::shorten()
{
	while (m_number.size() > 1 && m_number.back() == 0)
	{
		m_number.pop_back();
	}
}

int LN::comparison_operators(const LN& other) const
{
	if (m_isNegative != other.m_isNegative)
	{
		return m_isNegative ? -1 : 1;
	}
	for (int i = max(other.length(), length()) - 1; i >= 0; i--)
	{
		if (get(i) != other.get(i))
		{
			return (m_isNegative ? -1 : 1) * ((get(i) > other.get(i)) ? 1 : -1);
		}
	}
	return 0;
}

int LN::comparison_abs(const LN& other) const
{
	if (m_isNan || other.m_isNan)
		return 0;
	for (int i = max(other.length(), length()) - 1; i >= 0; i--)
	{
		if (get(i) != other.get(i))
			return (get(i) > other.get(i)) ? 1 : -1;
	}
	return 0;
}

void LN::add_operators(const LN& other, int signa, int signb)
{
	long long next = 0, now;
	for (size_t i = 0; i < max(length(), other.length()); i++)
	{
		now = next + signa * get(i) + signb * other.get(i);
		next = 0;
		if (now < 0)
		{
			now += m_block_size;
			next -= 1;
		}
		set(i, now % m_block_size);
		next += (now / m_block_size);
	}
	if (next)
	{
		add(next);
	}
	shorten();
	if (is_zero())
	{
		m_isNegative = false;
	}
}

void LN::div_mod(const LN& other, LN& div, LN& mod) const
{
	if (other == LN(0LL))
	{
		div.m_isNan = true;
		mod.m_isNan = true;
		return;
	}
	if (other.comparison_abs(*this) > 0)
	{
		div = LN(0LL);
		mod = *this;
		return;
	}

	LN d = bin_search(other);
	div = d;
	mod = *this - other * div;
}

int LN::sign() const
{
	return (m_isNegative ? -1 : 1);
}

LN LN::abs() const
{
	LN result = *this;
	result.m_isNegative = false;
	return result;
}

bool LN::is_zero() const
{
	for (int i = length() - 1; i >= 0; i--)
	{
		if (get(i) != 0)
		{
			return false;
		}
	}
	return true;
}

bool LN::any_isNan(const LN& other) const
{
	return !(m_isNan || other.m_isNan);
}

LN LN::bin_search(const LN& b) const
{
	LN l = LN(0LL);
	LN r = (*this).abs() + 1;
	while (l + LN(1LL) < r)
	{
		LN m = (l + r) / 2LL;
		if ((m * b).abs() <= (*this).abs())
		{
			l = m;
		}
		else
		{
			r = m;
		}
	}
	if (m_isNegative != b.m_isNegative)
	{
		return -l;
	}
	return l;
}
