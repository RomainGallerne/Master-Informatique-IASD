for c in 100 200 300
do
    echo "Generation des fichiers pour c=$c"
    for ((t=100;t<=600;t+=5));
    do
    mkdir -p CSP/"nbContraintes-$c"
    ./urbcsp 40 25 $c $t 15 > CSP/"nbContraintes-$c"/"csp-40-25-$c-$t-15.txt"
    done
done
echo "Fichiers CSP generes"







