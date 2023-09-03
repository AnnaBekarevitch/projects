#include "LN.h"
#include "return_codes.h"

#include <cstring>
#include <fstream>
#include <stack>
#include <stdexcept>
#include <string>

bool isInt(std::string& s)
{
	if (s.size() == 0)
	{
		return false;
	}
	if (s.size() == 1 && s[0] == '-')
	{
		return false;
	}
	for (int i = (s[0] == '-' ? 1 : 0); i < s.size(); i++)
		if (!(('0' <= s[i] && s[i] <= '9') || ('A' <= s[i] && s[i] <= 'F') || ('a' <= s[i] && s[i] <= 'f')))
			return false;
	return true;
}

signed main(int argc, char* argv[])
{
	if (argc != 3)
	{
		fprintf(stderr, "The parameter or number of parameters is incorrect\nExpected: <name1>.txt <name2>.txt\n");
		return ERROR_PARAMETER_INVALID;
	}
	std::ifstream myfile(argv[1]);
	std::ofstream out;

	myfile.exceptions(std::ifstream::badbit);
	out.exceptions(std::ifstream::badbit);
	if (myfile.is_open())
	{
		try
		{
			std::string s;
			std::stack< LN > st;
			while (getline(myfile, s))
			{
				if (isInt(s))
				{
					st.push(LN(s.c_str()));
					continue;
				}
				LN a = st.top();
				st.pop();
				if (s == "_")
				{
					st.push(-a);
					continue;
				}
				else if (s == "~")
				{
					st.push(~a);
					continue;
				}
				LN b = st.top();
				st.pop();
				if (s == "+")
				{
					st.push(a + b);
				}
				else if (s == "-")
				{
					st.push(a - b);
				}
				else if (s == "*")
				{
					st.push(b * a);
				}
				else if (s == "/")
				{
					st.push(a / b);
				}
				else if (s == "%")
				{
					st.push(a % b);
				}
				else if (s == "==")
				{
					st.push(LN(b == a));
				}
				else if (s == "<")
				{
					st.push(LN(a < b));
				}
				else if (s == "<=")
				{
					st.push(LN(a <= b));
				}
				else if (s == ">")
				{
					st.push(LN(a > b));
				}
				else if (s == ">=")
				{
					st.push(LN(a >= b));
				}
				else if (s == "!=")
				{
					st.push(LN(b != a));
				}
				else
				{
					fprintf(stderr,
							"The data in input file is incorrect\nCheck format in "
							"https://docs.google.com/document/d/1uJy6tArPNY94GoymgrukUr6Y-lQWbexhX8-dQTxgAME/"
							"edit#");
					return ERROR_DATA_INVALID;
				}
			}

			myfile.close();

			out.open(argv[2]);

			if (out.is_open())
			{
				while (!st.empty())
				{
					LN a = st.top();

					st.pop();
					out << a << '\n';
				}
				out.close();
			}
			else
			{
				fprintf(stderr, "File can't be opened\n");
				return ERROR_CANNOT_OPEN_FILE;
			}
		} catch (std::bad_alloc& error)
		{
			if (out.is_open())
			{
				out.close();
			}

			if (myfile.is_open())
			{
				myfile.close();
			}
			fprintf(stderr, "Not enough memory\n");
			return ERROR_OUT_OF_MEMORY;
		} catch (std::ifstream::failure e)
		{
			fprintf(stderr, "Exception opening/reading/closing file\n");
			if (myfile.is_open())
			{
				myfile.close();
			}
			if (out.is_open())
			{
				out.close();
			}
		}
	}
	else
	{
		fprintf(stderr, "File can't be opened\n");
		return ERROR_CANNOT_OPEN_FILE;
	}
	return SUCCESS;
}
