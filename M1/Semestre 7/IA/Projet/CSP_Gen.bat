for /L %%T in (150,3,312) do (
	for %%c in (200 250 300) do (
		urbcsp 35 17 %%c %%T 10 > CSP/"nbContraintes-%%c"/"csp-25-40-%%c-%%T-10.txt"
		)
	)
)
pause