# TreeStructuresAndSkipList

## SkipList

Binarne drzewa przeszukiwań pozwalają na szybkie wstawianie, wyszukiwanie oraz
usuwanie elementów w czasie O(log N), zależnym od wysokości drzewa. Niestety, ta
podstawowa struktura bardzo łatwo potrafi zostać zdegradowana, co redukuje jej
efektywność do czasu liniowego. Istnieją dedykowane mechanizmy zachowywania balansu
implementowane w strukturach takich jak: drzewa AVL, B-drzewa, czy drzewa czerwonoczarne, ale są one znacznie bardziej skomplikowane, przez co przeważnie wykorzystywane są
już istniejące implementacje. Istnieją jednak struktury danych, które pozwalają na uzyskanie
średniej logarytmicznej złożoności operacji będąc przy tym prostsze do napisania. Przykładem
takiej struktury danych jest lista z przeskokami.

Skip List jest probabilistyczną strukturą danych stanowiącą
modyfikację podstawowej, uporządkowanej listy jednokierunkowej. Modyfikacja polega na
rozszerzeniu wierzchołka o dodatkowe referencje do dalszych węzłów listy. Dzięki temu,
uwzględniając fakt, że lista jest zawsze uporządkowana, można szybko przeskakiwać duże jej
fragmenty. Każdy z węzłów listy przechowuje wartość oraz przynajmniej
jedną referencję na kolejny węzeł. 

Dla listy uporządkowanej dowolna
operacja na niej ma złożoność liniową O(N). W przykładowej liście z przeskokami, można
sterować długością przeskoku oraz ich liczbą na poziomie 1. Balans uzyskuje się przyjmując
długość przeskoku równą √𝑁, co daje jednocześnie √𝑁 węzłów na poziomie 1 (liczba
przeskoków razy ich długość musi dać liczbę elementów listy). Oznacza to, że taka lista ma
złożoność operacji rzędu O(√𝑵). Dodając większą liczbę poziomów, dla dużej liczby
elementów, uzyskamy średni czas operacji rzędu O(log N).

### Operacje:

- **Wyszukiwanie** - Podstawową operacją wykonywaną na listach z przeskokami jest wyszukiwanie. Jego idea sprowadza się do pozostawania na możliwie najwyższym poziomie listy do czasu, aż przeskok do kolejnego węzła nie spowodował by pominięcia fragmentu z szukaną wartością. Algorytm wyszukiwania składa się z poniższych kroków:  
1. Rozpocznij na najwyższym poziomie w głowie listy,  
2. Powtarzaj dopóki nie znaleziono węzła lub poziom >= 0:  
a. Jeżeli referencja na kolejny węzeł jest pusta, zejdź na niższy poziom,  
b. Jeżeli wartość w kolejnym węźle jest większa (większa równa) od szukanej, zejdź
na niższy poziom,  
c. Jeżeli wartość w kolejnym węźle jest mniejsza lub równa (mniejsza) szukanej,
przejdź do tego węzła.  
3. Jeżeli znaleziono węzeł o szukanej wartości – zwróć wartość, w przeciwnym
przypadku zgłoś brak szukanej wartości.  

- **Usuwanie** - Algorytm usuwania węzła jest w zasadzie identyczny do swojego odpowiednika dla list jednokierunkowych – znajdź węzeł i przepnij do jego poprzednika węzeł kolejny. Oczywiście trudność leży w tym, że lista z przeskokami posiada wielokrotne połączenia, które należy przepiąć do odpowiednich węzłów. Algorytm można zrealizować na kilka sposobów, np. istnieje wersja z pomocniczą tablicą przechowującą referencje na węzły do aktualizacji.  W niniejszym dokumencie przedstawiono jednak wersję rekurencyjną, która takiej dodatkowej pamięci nie wymaga. Algorytm usuwania elementu:  
1. Znajdź element na liście wykorzystując podstawową wersję algorytmu
wyszukiwania (schodzącą na najniższy poziom),  
2. Jeżeli węzła nie ma na liście – zakończ,  
3. W przeciwnym przypadku, wracając z rekurencji metody wyszukiwania wykonuj
następujące kroki:  
a. Jeżeli wracasz do węzła, który jest pierwszym węzłem odwiedzanym na danym
poziomie podczas powrotu – przepnij referencję z danego poziomu usuwanego
węzła do odwiedzanego węzła,  
b. Jeżeli usuwany węzeł nie ma już więcej poziomów lub poziom się nie zmienił –
nic nie rób.  

- **Wstawianie** - W odróżnieniu od przeszukiwania i usuwania, wstawianie nie jest algorytmem. Wynika to z faktu zastosowania losowości w trakcie tworzenia węzła. Ta losowość daje nam statystyczną gwarancję na średnią złożoność O(log N). Dla danej listy z przeskokami ustalany jest parametr 𝑝 ∈ ⟨0, 1) określający prawdopodobieństwo dodania kolejnego poziomu do węzła. Na jego podstawie ustalana jest liczba poziomów dla nowo tworzonego węzła, przy czym szansa na „wyższe” węzły maleje wykładniczo (𝑝𝑛, n – liczba poziomów). Wybór wysokości uzyskiwany jest na drodze rzutu monetą – poziomy dodawane są do czasu, aż nie wypadnie wynik o prawdopodobieństwie 1 − 𝑝. Metoda opisana jest poniżej:  
1. Liczba poziomów := 1  
2. Dopóki rand() < p: Liczba poziomów := Liczba poziomów + 1  
3. Zwróć Liczba poziomów

Funkcja rand() jest generatorem liczb pseudolosowych z przedziału ⟨0, 1). Mając ustaloną liczbę poziomów węzła, taki węzeł jest tworzony i zostaje wstawiony zgodnie z poniższym algorytmem:  
1. Znajdź miejsce na liście, w którym element ma zostać wstawiony, wykorzystując podstawową wersję algorytmu wyszukiwania (schodzącą na najniższy poziom),  
2. Jeżeli znaleziono węzeł o danej wartości – zgłosić błąd,  
3. Jeżeli węzła nie znaleziono, to wracając z rekurencji wykonuj następujące czynności:  
a. Jeżeli wracasz do węzła, który jest pierwszym węzłem odwiedzanym na danym poziomie podczas powrotu – wepnij nowy węzeł między węzeł odwiedzany, a jego następnik na danym poziomie,  
b. Jeżeli poziom się nie zmienił – nic nie rób.  
4. Jeżeli wrócono do głowy listy, a wstawiony węzeł ma jeszcze niepołączone poziomy – dodaj poziomy do głowy i podepnij do nich utworzony węzeł.   Jak widać, operacja wstawiania węzła jest analogiczna do usuwania węzła z listy.

### Podsumowanie  
Lista z przeskokami jest prostą, ale ciekawą strukturą danych pozwalającą na efektywne wstawianie, wyszukiwanie i usuwanie elementów. Wykorzystuje ona statystykę do zagwarantowania średniej złożoności O(log N). Oznacza to jednak, że efektywność nie jest zapewniona w każdym przypadku i ostatecznie może się zdarzyć, że operacje osiągną górną granicę O(N). Ze względu na wykorzystanie podstaw statystycznych, listy z przeskokami najlepiej działają pracy w sytuacji, gdy elementów jest bardzo dużo. Dzięki temu znalazły zastosowania w systemach zarządzania bazami danych. Należy podkreślić, że zysk czasowy wiąże się jednak z narzutem pamięciowym. W średnim przypadku mamy złożoność O(N), a w najgorszym O(N log N) [1].

## Zadania do wykonania  
Celem listy jest porównanie pod względem czasu efektywności operacji wybranych struktur
– drzewa BST, drzewa czerwono-czarnego oraz listy z przeskokami. Implementacje mają
realizować strukturę słownika – kolekcji przechowującej pary klucz, wartość i pozwalające na
efektywne wstawianie, usuwanie oraz odczytywanie wartości o danym kluczu.

1. Zdefiniuj interfejs IDisctionary<K, V> dla kolekcji zboru. Ma on zawierać operacje:
• V insert(K key, V value) – operacja wstawienia pary key, value do słownika.
Dodanie istniejącego klucza powoduje zwrócenie starej wartości (lub null, gdy
takowej nie ma) i zastąpienie jej value. Wartość null jest uznawana za
niepoprawną,
• V get(K key) – operacja zwraca wartość przechowywaną pod danym kluczem. Jeśli
klucz nie występuje w kolekcji, zwracane jest null,
• V remove(K key) – operacja usunięcia element o zadanym kluczu. Zwraza
przechowywaną wartość lub null.

2. Wykorzystaj implementację drzewa BST<T> z poprzedniej listy do stworzenia klasy
BSTDictionary<K, V> implementującą interfejs IDictionary<K, V>. W drzewie BST
przechowuj pary klucz, wartość, a porównania oprzyj o komparator, który porównuje
jedynie klucze. Komparator ten powinien opakowywać dedykowany komparator dla
typu K.

4. Stwórz klasę RBDictionary<K, V> implementującą interfejs IDisctionary<K, V> za
pomocą drzewa czerwono-czarnego. Wykorzystaj gotową implementację tej kolekcji
z biblioteki standardowej języka JAVA – TreeMap<K, V>.

5. Stwórz klasę SkipList<T> implementującą strukturę listy z przeskokami. Operacje listy
mają być zrealizowane zgodnie z opisem podanym w części wprowadzającej tj. nie
zawierać pomocniczych kolekcji węzłów do aktualizacji. Konstruktor klasy ma
przyjmować parametr p. Na jej podstawie stwórz kolekcję SkipDictionary<K, V>
implementującą interfejs IDictionary<K, V>. Wykorzystaj podejście z parami i
komparatorami jak dla drzewa BST.
