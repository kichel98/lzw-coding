Program jest napisany w języku Java (pisane na wersji 11, wymagana na pewno przynajmniej Java 8).

Implementacja algorytmu kompresji LZW z kodowaniem uniwersalnym.
Wersja na ocenę 5.

Kompilacja:
javac kikd/lista3/*.java

Uruchomienie:
java kikd.lista3.Main --encode (--delta/--gamma/--omega/--fib) file.in file.enc
oraz
java kikd.lista3.Main --decode (--delta/--gamma/--omega/--fib) file.enc file.dec

autor: Piotr Andrzejewski
nr indeksu: 244931