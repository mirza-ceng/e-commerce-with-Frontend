<!-- Repo için kısa ve öz Copilot yönergeleri (Türkçe) -->
# Copilot yönergeleri (bu depo)

Bu depo, sorumluluklara göre ayrılmış bir Spring Boot backend projesidir. Değişiklikleri minimal tutun, veritabanı eşlemelerini koruyun ve mevcut servislerdeki desenleri izleyin.

**Genel bakış**
- **Paketler**: `Bussiness` (servis katmanı), `DataAccess` (Spring Data repository'leri), `Entities` (JPA varlıkları), `Exception` (özel istisnalar), `RestApi` (controllerlar).
- **Veri akışı**: `RestApi` altındaki controllerlar `Bussiness/*Service` sınıflarını çağırır. Servisler `DataAccess/*Repository` ile `Entities/*.java`'yı kalıcı hale getirir.
- **Persistans**: Jakarta/JPA varlıkları ve Spring Data repository'leri kullanılıyor. Many-to-one ilişkiler proje içinde genellikle LAZY olarak ele alınır.

**Sık kullanılacak dosyalar**
- `Application.java` — uygulama giriş noktası.
- `Bussiness/OrderService.java`, `UserService.java`, `ProductService.java`, `CartItemService.java`, `OrderItemService.java` — çekirdek iş mantığı. Servisler `@Service` ile işaretli ve genelde public metotlarda `@Transactional` kullanıyor.
- `DataAccess/*Repository.java` — Spring Data JPA repository'leri (CRUD).
- `Entities/*.java` — JPA eşlemeleri. Örnek: `Order` sınıfı için `orderItems` üzerinde `cascade = CascadeType.ALL` ve `orphanRemoval = true` kullanımı var.

**Projeye özel kurallar / dikkat edilmesi gerekenler**
- Enum ve isimlendirme: bazı enum sabitleri ve roller Türkçe karakter içeriyor (ör. `Order.OrderStatus` içinde `PENDİNG`, `SHİPPED` benzeri). Enum isimlerini rastgele yeniden adlandırmayın — DB'de saklanan string değerleri ve JSON (de)serializasyonu etkilenebilir.
- ID tipi: varlıklar `long` primitif id kullanıyor (`product_id`, `order_id` vb.). İmza değişikliklerinde id tiplerini tutarlı kullanın.
- Transactional deseni: Servis katmanında halka açık işlemler `@Transactional` ile sınırlandırılmış. Transaction sınırlarını servis metotlarında tutmaya özen gösterin.
- Cascade davranışı: `Order` → `OrderItem` ilişkisi cascade edilmiştir; servislerde bu ilişkiye göre persist/merge davranışı bekleniyor. Kalıcılık sırasını değiştirirseniz FK kısıtlarını doğrulayın.
- Fetch davranışı: Birçok association LAZY; lazy koleksiyonlara transaction dışında erişilirse `LazyInitializationException` alabilirsiniz. Gerekirse repository'de `JOIN FETCH` kullanın.

**Ajana özgü sık görevler**
- Varlık alanı eklerseniz/ değiştirirseniz JPA anotasyonlarını güncelleyin; bu repo migrasyon aracı içermiyor, dolayısıyla veritabanı şemasıyla uyumlu olmasına dikkat edin.
- Servisleri düzenlerken mevcut deseni takip edin: eksik kaynaklarda `getById(...)` metotları `ResourceNorFoundException` fırlatıyor; halka açık servis metotlarını `@Transactional` ile işaretleyin ve repository `.save()` çağrılarını kullanın.
- Sipariş/stok mantığını değiştirirken şu metodlara dikkat edin: `ProductService.stockControl(...)`, `OrderService.createOrder(...)`, `OrderItemService.cartItemToOrderItem(...)`, `CartItemService.*`. Bu parçalar birbirine bağlı çalışır — değişiklikleri uçtan uca test edin.

**Çalıştırma (geliştirici komutları)**
- Windows PowerShell üzerinde tipik Spring Boot başlatma:
```powershell
.\mvnw.cmd spring-boot:run
# veya sistemde Maven yüklüyse:
mvn spring-boot:run
```

**Referans örnek dosyalar**
- `Entities/Order.java` — `orderItems` için cascade ve orphanRemoval kullanımı
- `Entities/OrderItem.java` — satın alma anında ürünün `price` ve `quantity` bilgisinin snapshot olarak saklanması
- `Bussiness/ProductService.java` — stok kontrolü ve ürün ekleme yetki kontrolleri
- `Bussiness/UserService.java` — kullanıcı oluşturma, giriş ve sipariş iptali akış örnekleri

**Kullanıcıya sormanız gereken durumlar**
- DB sütunu, enum sabiti veya cascade/fetch davranışını değiştirecek bir refactor gerekiyorsa önce kullanıcıya sorun; veri migrasyonu veya konfigürasyon değişikliği gerekebilir.
- Ortak davranış (logging/validation) eklenecekse önce servis katmanında uygulama yapın; controller'lar olabildiğince ince bırakılmalı.

Bu yönergelerde eksik veya netleştirilmesini istediğiniz bir bölüm varsa söyleyin; dosyayı isteğinize göre güncellerim.

