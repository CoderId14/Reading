<h1>
Reading
</h1> 
<h2>Spring Boot, MySQL, Spring Security, JWT, JPA, Rest API</h2>

Steps to SetUp

1. Clone the application:

>   `   git clone https://github.com/CoderId14/Reading.git
    `
2. Create Mysql database
    >**src/main/resources/database.sql**
3. Change Mysql username and password as your
   
    >open **src/main/resources/application.yaml**

    >change username and password
4. Run application using maven
    >cd to project

   >`mvn spring-boot:run`


**Auth**

| Method        |       Url        | Description |     |
|---------------|:----------------:|------------:|-----|
| POST          | /api/auth/signin |     Sign In |     |
| POST          | api/auth/signup  |     Sign Up |     |

User

| Method |      Url       |                Description |     |
|--------|:--------------:|---------------------------:|-----|
| GET    |   /api/users   | Get All users( Admin only) |     |
| POST   | /api/user/save |   Create user( Admin only) |     |
| POST   | /api/role/save |                  Save role |     |



News

| Method |           Url           |                                          Description |     |
|--------|:-----------------------:|-----------------------------------------------------:|-----|
| GET    |        /api/news        |                                         Get all news |     |
| GET    | /api/news/category/{id} |                                 Get News by category |     |
| GET    |   /api/news/tags/{id}   |                                      Get News by tag |     |                          |     |
| POST   |        /api/news        |                                Add New(After signIn) |     |
| PUT    |     /api/news/{id}      |              Update New( Admin or User created that) |     |
| DELETE |     /api/news/{id}      |              Delete New( Admin or User created that) |     |


Chapter

| Method |              Url               |                                           Description |     |
|--------|:------------------------------:|------------------------------------------------------:|-----|
| GET    |   /api/news/{newId}/chapter    |                                Get all Chapter of New |     |
| GET    | /api/news/{newId}/chapter/{id} |                                    Get Chapter of New |     |
| POST   |   /api/news/{newId}/chapter    |                 Add Chapter(Admin or User created New |     |
| PUT    | /api/news/{newId}/chapter/{id} |            Update Chapter( Admin or User created New) |     |
| DELETE | /api/news/{newId}/chapter/{id} |            Delete Chapter( Admin or User created New) |     |


Category

| Method |           Url            |                                 Description |     |
|--------|:------------------------:|--------------------------------------------:|-----|
| GET    |     /api/categories      |                          Get all Categories |     |
| GET    |   /api/categories/{id}   |                                Get Category |     |
| POST   |     /api/categories      |      Add Category(Admin or User created New |     |
| PUT    |   /api/categories/{id}   | Update Category( Admin or User created New) |     |
| DELETE |   /api/categories/{id}   | Delete Category( Admin or User created New) |     |


Attachment


| Method |         Url          |                               Description |     |
|--------|:--------------------:|------------------------------------------:|-----|
| GET    |   /api/attachment    |                        Get all attachment |     |
| GET    | /api/attachment/{id} |                            Get attachment |     |
| POST   |   /api/attachment    |   Add attachment(Admin or User had login) |     |
| PUT    | /api/attachment/{id} | Update attachment( Admin or User created) |     |
| DELETE | /api/attachment/{id} | Delete attachment( Admin or User created) |     |
