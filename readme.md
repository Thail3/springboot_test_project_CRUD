เริ่มต้น ให้ clone project จาก github มาไว้ในเครื่อง
```bash
git clone https://github.com/your-repo/your-project.git
```

ติดตั้ง brew gradle เพื่อใช้งาน
```bash
brew install gradle
```

สร้าง database mysql โดยมี user root และ password newpassword และ database name db_test
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/db_test
spring.datasource.username=root
spring.datasource.password=newpassword
```

วิธีการ start server ด้วย brew ใน vscode
```
./gradlew bootRun
```

เช็ค status ของ Database
```bash
brew services list
```

วิธีการ start server mysql
```bash
brew services start mysql
```

วิธีการ stop server mysql
```bash
brew services stop mysql
```