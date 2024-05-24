# FYP - Manchester Exploration

## User Center -- Back-end

Spring + Spring MVC + Mybatis + Mybatis-Plus + SpringBoot + MySQL\
VUE 3 + Arco Design Pro (Vite, Axios ...)

### Backend return and exception handling

#### Generic return object

```
{
	"data" : {
	
	},
	"code" : , // Status Code
	"message": "", // Status Message
}
```



#### Status code definition

``` java
SUCCESS(20000, "Success"),

REQUEST_ERROR(40000, "Request error"),
PARAMS_EMPTY_ERROR(40001, "Input params is empty"),
USER_AUTH_ERROR(40002, ""),

NO_RESULTS_ERROR(40100, "Username or Password Wrong"),

DUPLICATE_USER_ERROR(40200, "User info duplicated"),
USERNAME_LENGTH_ERROR(40201, "Username length need over 4"),
USERNAME_INVALID_ERROR(40202, "Username has special character"),
PASSWORD_LENGTH_ERROR(40203, "Password length need over 8"),
PASSWORD_INVALID_ERROR(40204, "Password needs at least two types of numeric, letter and special character"),
PASSWORD_COMPARED_ERROR(40205, "Checked password is not equal to the original one"),

SYSTEM_ERROR(50000, "Something wrong in the system"),
```

#### Encapsulated business exception handling and global exception handling



### Login / Register

#### Database Design

User Table (user)：

* id (pk) bigint auto_increament
* username (login username) varchar
* user_password (login password) varchar
* nickname varchar
* gender tinyint
* avatar_url varchar
* phone varchar
* email varchar
* account_status (account status 0 - activate) tinyint
* create_time (account creation time) datetime
* update_time (account data update time) datetime
* delete_state (logical deletion 0 - not 1 - deleted) 
* role (user role 0 - user 1 - admin)
* tags (user tag) varchar

``` mysql
create table if not exists manchester_exploration.user
(
    id             bigint auto_increment comment 'column id'
        primary key,
    username       varchar(255)                       null comment 'login username',
    user_password  varchar(255)                       not null comment 'login password',
    nickname       varchar(255)                       null comment 'user nickname',
    gender         tinyint                            null comment 'user gender 0 - female 1 - male', 
    avatar_url     varchar(1024)                      null comment 'user avatar url',
    phone          varchar(128)                       null comment 'user phone',
    email          varchar(255)                       null comment 'user email',
    account_status tinyint  default 0                 not null comment 'user account status',
    create_time    datetime default CURRENT_TIMESTAMP null comment 'user account create time',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'user account update time',
    delete_state   tinyint  default 0                 null comment 'user account delete state',
    user_role      tinyint  default 0                 not null comment 'user role 0 - common, 1 - admin',
    tags           varchar(1024)                      null comment 'Tags JSON list'
)
    comment 'user_table';
```

Use the MybatisX plug-in to automatically generate pojo entity objects based on the database, mapper, mapper.xml, service and serviceImpl



#### Registration logic

1. The user enters the account, password, and checked password on the front end ---- TODO
2. Verify whether the user account password meets the requirements
   * All inputs cannot be empty (tested)
   * Account name must be no less than 4 characters (tested)
   * Account name cannot contain special symbols (tested)
   * Accounts cannot be duplicated (tested)
   * The password must have at least 2 types of numbers, letters, and special characters (tested)
   * Password length should be no less than 8 characters (tested)
   * The password entered twice is the same (tested)
   * ........
3. Password is encrypted (encryption during transmission + backend encryption)   --------- TODO transmission
4. Database insert user data



#### Login / Logout logic

1. The user enters the account and password on the front end (the account can use email --- TODO)
   1. Accept parameters: user account, password
   2. Request type: POST
   3. Request body: data in JSON format
   4. Return value: basic user information (desensitization)
2. To verify whether the user account and password are legal, compare them with the ciphertext password in the database.

   * All inputs cannot be empty
   * Account name must be no less than 4 characters
   * The password must have at least 2 of numbers, lowercase letters, uppercase letters, and special characters.
   * The password must be no less than 8 characters in length
3. Record the user’s login status (session) and save it on the server
2. Return user information (desensitization)

#### Controller encapsulation request

```
@RestController Suitable for writing restful style api, the return value defaults to json type
```

Serialization------------------------------------------------ (study)

Logout: Just remove the user's login status and then return to the front end



## Component design

1. User profile settings
2. User tag display
3. User Picture Gallery
4. POI tag display
5. Directly filter POIs based on tags
6. Blurred search POI
7. Map component
    * layer style switching
    * Show road data
    * Navigation + Positioning
    * Weather data display (current + forecast)
    * Public transport data display (bus + train)
8. Travel plan designation/verification
9. Past/current travel plan management



### Profile settings ---  TODO



###  label design

#### Tag table design (category table):

* id (pk) bigint auto_increament
* user_id (tag creation user) bigint  ---- Ordinary index
* tag_name varchar(256)    ---- unique index
* tag_node (tag list) varchar(256)
* tag_parent_id (parent tag id) bigint (-1 means this tag is parent node)
* create_time (account creation time) datetime
* update_time (account data update time) datetime
* delete_state (logical deletion 0 - not 1 - deleted) 

``` mysql
create table if not exists manchester_exploration.tags
(
    id            bigint auto_increment comment 'primary key'
        primary key,
    user_id       bigint                             null comment 'user id who creat this tag',
    tag_name      varchar(256)                       not null comment 'tag name',
    tag_node      varchar(256)                       not null comment 'tag id list of this tag family',
    tag_parent_id bigint                             not null comment 'parent id of this tag in this tag family (-1 means this is the parent tag)',
    create_time   datetime default CURRENT_TIMESTAMP not null comment 'creation time of this tag',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'tag update time',
    delete_state  tinyint  default 0                 null comment 'tag delete state',
    constraint tags_tag_name_uindex
        unique (tag_name) comment 'tag name must be unique'
)
    comment 'tag table';

create index tags_user_id_index
    on manchester_exploration.tags (user_id)
    comment 'index for user id';
```



#### Label Search Logic

* Allow users to use multiple tags, and they will be searched only if multiple tags exist (and)
* Allow users to use multiple tags, and any tag that exists can be searched (or)

