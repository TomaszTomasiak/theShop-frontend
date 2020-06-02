# theShop-frontend

Aplikacja jest prostym symulatorem sklepu internetowego. Funkcjonalności podzielono na dwie części:

administracyjną (admin)

Administrator ma do wglądu tabele z zamówieniami, użytkownikami, produktami i grupami produktów - wraz z możliwością możliwością ich modyfikowania i tworzenia. Dostępne są filtry, przyciski funkcyjne oraz informacje statystyczne. Administrator ma możliwość prawdzenia ważności konta mailowego użytkowników (zewnętrzne api sprawdzające adresy mailowe). Aplikacja wysyła powiadomienie do administratora o utworzeniu nowego użytkownika oraz złożeniu zamówienia. Codziennie administrator otrzymuje informację mailową (scheduler) o ilości dodanych użytkowników ostatniej doby oraz ilości i wartości zamówień z dnia poprzedniego.

biznesową (user)

Użytkownik może wyszukiwać produkty po nazwie lub grupie oraz dodawanć, usuwać i modyfikować produkty w koszyku. Po złożeniu zamówienia na maila użytkownika wysyłana jest informacja z potwierdzeniem zamówienia oraz listą zakupionych produktów. Ceny produktów oraz wartości zamówień przeliczane są na waluty według średniego kursu NBP (zewnetrzne API).

Uruchamianie: 

Pierwszą uruchamiamy aplikację beckendową (theShop port=4600), nastepnie aplikację frontendową (theShop-frontend port=4601). 

Otwieramy lokalnie pod adresem: http://localhost:4601/. 

Aplikacja backendowa korzysta z bazy danych MySql oraz następujących metod HTTP: GET, PUT, POST, DELETE.

Pokrycie kodu: > 80%

link do backendu (REST API): https://github.com/TomaszTomasiak/theShop

link do frontendu : https://github.com/TomaszTomasiak/theShop-frontend

ostatni commit na backend (REST API): https://github.com/TomaszTomasiak/theShop/commit/a0c5df87e8b79de85848cc74eb169158fc27ac6d

ostatni commit na frontend: https://github.com/TomaszTomasiak/theShop-frontend/commit/d639c57856c25f5776eab24e1dae75a5b3cd8ff9

Niestety nie udało się ukończyć w terminie ostatecznej wersji aplikacji frontendowej - zaplanowane dalsze prace rozwojowe.
