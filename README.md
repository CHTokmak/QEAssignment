## Bu Şablon Ne İçerir

Bu şablon içerisinde selenium, gauge, maven temelli bdd bir web test otomasyon şablonu bulunmaktadır.
Bu tool aynı zamanda test koşumları sonrası koşulan testlerin raporunu html dosyas olarak kayıt eder.
Ve test koşumları bittiğinde aktif tarayıcınızda test raporunu açar. Bu sebeple lütfen
test sonlarında açılacak yeni tarayıcıyı bekleyiniz.

## Kurulum

Pom dosyası içerisinde yer alan Java, Gauge, Gauge Pluginleri, Maven versiyonlarını bilgisayarına kurup yollarını tanımlayınız.

## Bu Template Üzerinde Çalışmak

### Elinizde Olanla Yapılabilinecekler

* Selenium 4 kullanılmakta ve bu sayede yeni ve güncel test yöntemlerini uygulayabilirsiniz.
* Raporlama toolu entegrasyonu sayesinde otomatik olarak koşumlarınızı raporlayabilirsiniz.
* Maven ve POM kullanımı sayesinde kendi dependencylerinizi verip yeni toollar ve kütüphaneler ekleyebilirsiniz.
* BDD yazımı ile birlikte ayrık test koşumları ve birleşik suitler koşabilirsiniz.
* Tag yapısı sayesinde raporlarınız ve loglarınızda testlei daha kolay seçip case tagları ile raporlayabilirsiniz.
* WebDriverManager kullanımı ile farklı web tarayıcılarında kolaylıkla koşum yapabilirsiniz.
* Selector mekanikleri json dosyalarına bağlı ve formatlanmış olduğu için burada vereceğiniz keylere ve elemnt türlerine göre xpath, ccs gibi farklı element typelar ile step-implementationlarınızda çalışabilirsiniz.
* Configuration.properties içerisindeki urller ve browser belirteçleri ile kodun içine girmeden de test bazınızı değiştirebilirsiniz.


### Yeni Senaryo Eklemek

Yeni senaryo eklemek için step implementation içerisinde yer alan stepleri cpt dosyaları içerisinde kullanıp
elementValues dosyası içerisinde yer alan .json dosyalarından kullanımınıza uygun keyleri bu stepler içerisine yerleştirerek concept dosyalrı hazırlayınız.
Daha sonrasında örnek spec dosyalarındaki format ile concept bdd lerinizi test içerisinde çağırabilirsiniz.

### Yaşayabileceğiniz Sorunlar ve İpuçları
* Safari ve Firefox için otomasyon izinleri verilmelidir.
* Target dosyası içerisinde yer alan extent-report dosyası boş görünse de silinmemeli dosya kayıt yönteminde dosya derleme için aracı konumda bulunmaktadır.
* Versiyon problemleri için pomda yer alan versiyonlara uygun kurulumlar yapınız.

### Çalıştırma
Spec dosyaları içerisinde yer alan oynatma tuşları ile test caseleri ve test suiteleri çalıştırabilirsiniz.
* 
```
mvn test
```
Komutu ile de testlerinizi çalıştırabilirsiniz