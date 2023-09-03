init(1) :- !.
init(MAX_N) :- is_prime(MAX_N, 2), asserta(prime(MAX_N)), N is MAX_N - 1, init(N), !.
init(MAX_N) :- 	N is MAX_N - 1, init(N), !.
is_prime(N, V) :- 
	MUL is V * V,
	MUL =< N,
	\+(0 is mod(N, V)), 
	is_prime(N, V + 1).
is_prime(N, V) :- 
	MUL is V * V,
	MUL > N.	
composite(N) :- \+ prime(N).
prime_divisors(1, []) :- !.
cube_divisors(1, []) :- !.
prime_divisors(V, [V]) :- prime(V), !.
prime_divisors(N, [V, U | T]) :- number(N), prime(V), 0 is mod(N, V), B is div(N, V), prime(U), V =< U, prime_divisors(B, [U | T]). 
prime_divisors(N, [V, U | T]) :- \+ number(N), prime(V), prime(U), V =< U, prime_divisors(B, [U | T]), N is B * V. 
cube_divisors(N, T) :- N1 is N * N, N2 is N1 * N, prime_divisors(N2, T).