<h1 style='background-color: rgba(55, 55, 55, 0.4); text-align: center'>API 명세서 </h1>

해당 API 명세서는 '렌트카 서비스'의 REST API를 명세하고 있습니다.

- Domain : <http://localhost:4000> 

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> Auth 모듈 </h2>  
  
- url : /api/rentcar/auth

***

#### - 로그인  
  
##### 설명

클라이언트로부터 사용자 아이디와 평문의 비밀번호를 입력받고 아이디와 비밀번호가 일치한다면 성공처리가되며 access_token과 해당 토큰의 만료 기간을 반환합니다. 만약 아이디 혹은 비밀번호가 하나라도 틀리다면 실패 처리됩니다. 서버 에러, 데이터베이스 에러, 토큰 생성 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/sign-in**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| userPassword | String | 사용자의 비밀번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/sign-in" \
 -d "userId=service123" \
 -d "userPassword=P!ssw0rd"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| accessToken | String | 사용자 토큰값 | O |
| expires | String | 만료기간 | O | 

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "accessToken": "${ACCESS_TOKEN}"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (로그인 정보 불일치)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "SF",
  "message": "Sign in Failed."
}
```

**응답 : 실패 (토큰 생성 실패)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "TF",
  "message": "Token creation Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 아이디 중복 확인  
  
##### 설명

클라이언트로부터 아이디를 입력받아 해당하는 아이디가 이미 사용중인 아이디인지 확인합니다. 중복되지 않은 아이디이면 성공처리를 합니다. 만약 중복되는 아이디라면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/id-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 중복 확인 할 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/id-check" \
 -d "userId=service123"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "DI",
  "message": "Duplicated Id."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 이메일 인증  
  
##### 설명

클라이언트로부터 이메일을 입력받아 해당하는 이메일이 이미 사용중인 이메일인지 확인하고 사용하고 있지 않은 이메일이라면 4자리의 인증코드를 해당 이메일로 전송합니다. 이메일 전송이 성공적으로 종료되었으면 성공처리를 합니다. 만약 중복된 이메일이거나 이메일 전송에 실패했으면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/email-auth**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 인증 번호를 전송할 사용자 이메일</br>(이메일 형태의 데이터) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/email-auth" \
 -d "userEmail=email@email.com"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (중복된 이메일)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "DE",
  "message": "Duplicated Email."
}
```

**응답 : 실패 (이메일 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "MF",
  "message": "Mail send Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 이메일 인증 확인
  
##### 설명

클라이언트로부터 이메일과 인증 번호를 입력받아 해당하는 이메일에 전송한 인증번호와 일치하는지 확인합니다. 일치한다면 성공처리를 합니다. 만약 일치하지 않는다면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/email-auth-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 인증 번호를 확인할 사용자 이메일 | O |
| authNumber | String | 인증 확인할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/email-auth-check" \
 -d "userEmail=email@email.com" \
 -d "authNumber=0123"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (이메일 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원가입
  
##### 설명

클라이언트로부터 아이디, 비밀번호, 이메일, 인증번호 입력받아 회원가입 처리를 합니다. 정상적으로 회원가입이 완료되면 성공처리를 합니다. 만약 중복된 아이디, 중복된 이메일, 인증번호 불일치가 발생하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/sign-up**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 아이디 | O |
| userPassword | String | 사용자 비밀번호 (영문+숫자 8~13자) | O |
| nickName | String | 사용자 닉네임 | O |
| userEmail | String | 사용자 이메일 (이메일 형태의 데이터) | O |
| authNumber | String | 인증 확인할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/sign-up" \
 -d "userId=service123" \
 -d "userPassword=Pa55w0rd" \
 -d "nickName=nickName" \
 -d "userEmail=email@email.com" \
 -d "authNumber=0123"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "DI",
  "message": "Duplicated Id."
}
```

**응답 : 실패 (중복된 이메일)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "DE",
  "message": "Duplicated Email."
}
```

**응답 : 실패 (이메일 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> user 모듈 </h2>

- url : /api/rentcar/user

***

#### - 아이디 찾기  
  
##### 설명

클라이언트로부터 이메일을 입력받아 해당하는 이메일이 존재하는 이메일인지 확인하고 존재하는 이메일이면 그에 해당하는 아이디를 반환합니다. 아이디 반환이 성공적으로 반환되면 성공처리를 합니다. 만약 존재하지 않는 이메일일 경우 실패처리를 합니다. 인증 실패, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/find-id**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 인증 번호를 전송할 사용자 이메일</br>(이메일 형태의 데이터) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/find-id" \
 -d "userEmail=email@email.com"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자의 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId": "service"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 이메일)**
```bash
HTTP/1.1 401 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NE",
  "message": "No Exist Email."
}
```

**응답 : 실패 (아이디 찾기 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 비밀번호 찾기
  
##### 설명

클라이언트로부터 아이디와 이메일을 입력받아 해당하는 아이디와 이메일이 데이터베이스에 존재하는지 확인합니다. 아이디와 이메일이 존재하면 성공처리를 합니다. 만약 아이디 또는 이메일이 존재하지 않으면 실패처리를 합니다. 인증 실패, 서버에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/find-password**

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| userEmail | String | 사용자의 이메일</br>(이메일 형태의 데이터) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/auth/find-password" \
 -d "userId=service123" 
 -d "userEmail=service123@naver.com" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (비밀번호 찾기 인증 실패)**
```bash
HTTP/1.1 401 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (존재하지 않는 아이디)**
```bash
HTTP/1.1 401 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NUI",
  "message": "No Exist User Id."
}
```

**응답 : 실패 (존재하지 않는 이메일)**
```bash
HTTP/1.1 401 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NE",
  "message": "No Exist Email."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***
#### - 비밀번호 재설정  
  
##### 설명

클라이언트로부터 비밀번호를 입력받아 기존의 비밀번호를 변경합니다. 변경에 성공하면 성공처리를 합니다. 만약 변경에 실패하면 실패처리를 합니다. 인증 실패, 서버에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **PUT**  
- URL : **/find-password/{userId}**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userPassword | String | 사용자의 비밀번호 | O |

###### Example

```bash
curl -v -X PUT "http://localhost:4000/api/rentcar/auth/find-password/${userId}" \
 -d "userPassword={userPassword}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (비밀번호 재설정 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 로그인 유저 정보 반환  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 받으면 해당 토큰의 작성자(subject)에 해당하는 사용자 정보를 반환합니다. 성공시에는 사용자의 아이디와 권한을 반환합니다. 인증 실패 및 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/user/" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자의 아이디 | O |
| userRole | String | 사용자의 권한 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId": "${userId}",
  "userRole": "${userRole}"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```


***

#### - 내 정보 보기

##### 설명


클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 로그인한 정보에 대한 내용을 반환합니다. 만약 내 정보 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 서버 에러, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/information**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/user/information" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

##### Path Variable
| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 아이디 | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자의 아이디 | O |
| userPassword | String | 사용자의 비밀번호 | O |
| nickName | String | 사용자의 닉네임 | O |
| userEmail | String | 사용자의 이메일 | O |
| userRole | String | 사용자의 권한 | O |
| joinPath | String | 가입 경로 | O |
| joinDate | Date | 사용자의 가입날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId": "admin",
  "userPassword": "qewr1234",
  "nickName": "nickname",
  "userEmail": "email@email.com",
  "userRole": "ROLE_USER"
  "joinPath": "HOME",
  "joinDate": "2024-05-14",
}
```

**응답 : 실패 (존재하지 않는 내 정보)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NI",
  "message": "No Exist Information."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 내 정보 비밀번호 수정

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 비밀번호를 입력받습니다. 비밀번호 수정에 성공하면 성공처리를 합니다. 만약 수정에 실패하면 실패처리 됩니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**
- URL : **/information/password-modify**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userPassword | String | 사용자의 비밀번호 | O |


###### Example

```bash
curl -v -X PUT "http://localhost:4000/api/rentCar/user/information/password-modify" \
 -H "Authorization: Bearer {JWT}" \
 -d "userPassword=qwer1234" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 내 정보)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NI",
  "message": "No Exist Information."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

#### - 내 정보 이메일 수정

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 이메일과 인증번호를 입력받고 인증번호가 일치하는지 확인합니다. 이메일 수정에 성공하면 성공처리를 합니다. 만약 존재하지 않는 내 정보, 중복된 이메일, 인가 실패, 인증 실패, 데이터베이스 에러가 발생하면 실패처리 합니다. 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**
- URL : **/information/email-modify**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 사용자의 이메일 | O |
| authNumber | String | 인증 확인할 인증 번호 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/rentCar/user/information/email-modify" \
 -H "Authorization: Bearer {JWT}" \
 -d "userEmail=service@naver.com" \ 
 -d "authNumber=1234"\
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 내 정보)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NI",
  "message": "No Exist Information."
}
```

**응답 : 실패 (중복된 이메일)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "DE",
  "message": "Duplicated Email."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 탈퇴하기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받고 요청을 보내면 해당하는 사용자 정보가 삭제됩니다. 만약 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패, 서버 에러가 발생하면 실패처리를 합니다.

- method : **DELETE**  
- URL : **/information/delete**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/user/information/modify" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 회원)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NU",
  "message": "No Exist User."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원 목록 리스트 불러오기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 작성일 기준 내림차순으로 회원목록 리스트를 반환합니다. 
만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/user/list" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header


| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userList | UserListItem[] | 회원 목록 리스트 | O |

**UserListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디  | O |
| nickName | String | 사용자의 닉네임  | O |
| userEmail | String | 사용자 이메일  | O |
| joinDate | String | 작성일</br>(yyyy-mm-dd 형태) | O |
| userRole | String | 사용자 권한 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userList": [
    {
      "userId" : "admin",
      "nickName": "nickname",
      "userEmail": "admin@email.com",
      "joinDate": "2024-05-02"
    }, ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원 상세 페이지 불러오기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 회원 상세 페이지에서 회원 아이디, 닉네임, 이메일, 가입 날짜를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터 유효성 검사 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/user/list/${userId}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header


| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자의 아이디  | O |
| nickName | String | 사용자의 닉네임  | O |
| userEmail | String | 사용자 이메일  | O |
| joinDate | String | 작성일</br>(yyyy-mm-dd 형태) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userList": [
    {
      "userId" : "service",
      "nickName": "nickname",
      "userEmail": "service@email.com",
      "joinDate": "2024-05-02"
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원 목록 리스트 삭제하기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 회원 목록의 순번을 입력받고 요청을 보내면 해당하는 회원 목록 리스트가 삭제됩니다. 만약 삭제에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터 유효성 검사 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/list/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable
| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | int | 사용자 아이디 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/user/list/${userId}" \

 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 회원)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NU",
  "message": "No Exist User."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원 목록 검색 게시물 리스트 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 검색어를 입력받고 요청을 보내면 작성일 기준 내림차순으로 제목에 해당 검색어가 포함된 회원목록 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/search**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| searchWord | String | 검색어 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/user/list/search?word=${searchWord}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userList | userListItem[] | 회원 목록 리스트 | O |

**userListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디  | O |
| nickName | String | 사용자의 닉네임  | O |
| userEmail | String | 사용자 이메일  | O |
| joinDate | String | 작성일</br>(yyyy-mm-dd 형태) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userList": [
    {
      "userId" : "service",
      "nickName": "nickname",
      "userEmail": "service@email.com",
      "joinDate": "2024-05-02"
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 회원)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NU",
  "message": "No Exist User."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> Reservation 모듈 </h2>  
  
- url : /api/rentcar/reservation  

***

#### - 마이페이지 예약 내역 보기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 작성일 기준 내림차순으로 예약 내역을 반환합니다. 만약 존재하지 않는 예약일 경우 실패를 반환합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/mylist**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/mylist" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userList | ReservationListItem[] | 회원 목록 리스트 | O |

**ReservationListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| carImageUrl | String | 차량 사진 | O |
| reservationCode | String | 예약번호 | O |
| nickName | String | 예약자의 닉네임 | O |
| rentCompany | String | 영업점 | O |
| reservationDate | String | 예약날짜 | O |
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "reservationState": "예약 완료",
  "carImageUrl": "image.jpg",
  "reservationCode": "1",
  "nickName": "nickname",
  "rentCompany": "민머리 철수 렌트카",
  "reservationDate": "24.05.14",
  "reservationStart": "2024-05-16",
  "reservationEnd": "2024-05-17",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 마이페이지 예약 상세 내역 보기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 예약 상세 내역을 반환합니다. 만약 존재하지 않는 예약일 경우 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/mylist/{reservationCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/mylist/${reservationCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| carImageUrl | String | 차량 사진 | O |
| nickName | String | 사용자(예약자)의 닉네임 | O |
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |
| carImageUrl | String | 차량 사진 | O |
| carName | String | 차량 이름 | O |
| carNumber | String | 차량 번호 | O |
| grade | String | 차량 등급 | O |
| carOil | int | 연비 | O |
| rentCompany | String | 렌트 업체 이름 | O |
| companyTelnumber | String | 렌트 업체 전화번호 | O |
| address | String | 렌트 업체 주소 | O |
| insuranceType | String | 보험 종류 | O |
| insurancePrice | String | 총 가격 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "nickName": "nickname",
  "reservationStart": "2024-05-16",
  "reservationEnd": "2024-05-17",
  "carImageUrl": "image.jpg",
  "carName": "캐스퍼",
  "carNumber": "123하1234",
  "grade": "소형",
  "carOil": "14",
  "rentCompany": "민머리 철수 렌트카",
  "companyTelnumber": "064-727-5680",
  "address": "제주특별자치도 제주시 용문로 8",
  "insuranceType": "일반자차",
  "insurancePrice": "62000",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 마이페이지 예약 취소하기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 예약번호 입력받고 예약 상태 수정에 성공하면 성공처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PATCH**  
- URL : **/mylist/{reservationCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| reservationCode | String | 수정할 예약 번호 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/rentcar/reservation/mylist/${reservationCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| reservationState | String | 예약 상태 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "reservationState": "예약 취소 대기"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 관리자페이지 예약 관리 리스트 불러오기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 작성일 기준 내림차순으로 예약목록 리스트를 반환합니다. 만약 예약 존재하지 않을 경우 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/list" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| reservationUserList | ReservationUserListItem[] | 회원 목록 리스트 | O |

**ReservationUserListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| reservationCode | String | 예약 번호 | O |
| rentCompany | String | 업체 이름 | O |
| carName | String | 차량 이름 | O |
| carNumber | String | 차량 번호 | O |
| reservationStart | String | 예약 시작일 </br>(yyyy-mm-dd 형태) | O |
| reservationEnd | String | 예약 종료일 </br>(yyyy-mm-dd 형태) | O |
| nickName | String | 작성자 닉네임  | O |
| userId | String | 사용자 아이디 | O |
| reservationState | String | 예약 상태 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userList": [
    {
      "reservationCode": "1",
      "rentCompany": "민머리 철수 렌터카",
      "carName": "캐스퍼",
      "carNumber": "123하1234",
      "reservationStart": "2024-05-16",
      "reservationEnd": "2024-05-17",
      "nickName": "nickName",
      "userId": "service",
      "reservationState": "예약 완료",
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 관리자페이지 예약 상세 불러오기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 작성일 기준 내림차순으로 예약목록 리스트를 반환합니다. 만약 존재하지 않는 예약일 경우 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/{reservationCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/{reservationCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header


| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| reservationCode | String | 예약 번호 | O |
| rentCompany | String | 업체 이름 | O |
| carName | String | 차량 이름 | O |
| carNumber | String | 차량 번호 | O |
| reservationStart | String | 예약 시작일 </br>(yyyy-mm-dd 형태) | O |
| reservationEnd | String | 예약 종료일 </br>(yyyy-mm-dd 형태) | O |
| userId | String | 사용자 아이디 | O |
| reservationState | String | 예약 상태 | O |
| insurancePrice | int | 예약한 가격 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userList": [
    {
      "reservationCode": "1",
      "rentCompany": "민머리 철수 렌터카",
      "carName": "캐스퍼",
      "carNumber": "123하1234",
      "reservationStart": "2024-05-16",
      "reservationEnd": "2024-05-17",
      "userId": "service",
      "reservationState": "예약 완료",
      "insurancePrice": "￦27,000"
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 관리자페이지 예약 목록 리스트 삭제하기

##### 설명

클라이언트는 Request Header의 Authorization 필드에 Bearer 토큰을 포함하여 예약 목록의 순번을 입력하고 요청을 보내면 해당하는 예약 목록이 삭제됩니다.
 만약 존재하지 않는 예약일 경우 실패 처리를 합니다. 인증 실패, 인가 실패 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/{reservationCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/reservation/${reservationCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header


| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 예약)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NR",
  "message": "No Exist Reservation."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 인기 차량 리스트 불러오기
   
##### 설명

클라이언트로부터 요청을 보내면 차량의 사진, 차량명, 차량 예약 수를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/popular**  

##### Request

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/popular" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body
| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| popularCarList | PopularCarListItem[] | 인기차량 리스트 |O |

**PopularCarListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| carImageUrl | String | 차량 이미지 | X |
| carName | String | 차량 이름 | O |
| totalReservationCount | int | 차량 총 예약 수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "popularCarList": [
    {
      "carImageUrl": "image.jpg",
      "carName": "아반떼",
      "reservationCount": "20"
    }, ...
  ]
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 차량 검색 결과 불러오기
  
##### 설명

클라이언트로부터 검색 카테고리(예약 시작일, 예약 종료일)를 입력받고 요청을 보내면 각 차량별 보험별 가격 검색 결과를 데이터베이스 순서대로 (차량 코드) 반환합니다. 존재하지 않는 차량일 경우 실패를 반환합니다. 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/search**  

##### Request

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/search" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| reservationCarList | ReservationCarListItem[] | 차량 검색 결과 | O |

**ReservationCarListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| carName | String | 차량명 | O |
| carImageUrl | String | 차량 이미지 | X |
| normalPrice | int | 일반 자차 보험 가격 | O |
| luxuryPrice | int | 고급 자차 보험 가격 | O |
| superPrice | int | 슈퍼 자차 보험 가격 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "boardList": [
    {
      "carName": "아반떼",
      "carImageUrl": "avante_image.jpg",
      "normalPrice": 25100,
      "luxuryPrice": 45100,
      "superPrice": 75100
    }
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 차량)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Vehicle."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 보험별 가격 검색 결과 불러오기

##### 설명

클라이언트로부터 차량명과 이미지, 보험을 입력받고(클릭) 요청을 보내면 해당 차량의 업체별 가격 검색 결과를 업체명을 내림차순으로 반환합니다. 만약 존재하지 않는 차량일 경우 실패처리를 합니다. 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/search/{carName}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| carName | String | 차량명 | O |

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/search/{carName}" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| priceList | priceListItem[] | 업체별 가격 검색 결과 | O |

**PriceListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| carImageUrl | String | 차량 이미지 | X |
| carName | String | 차량명 | O |
| fuelType | String | 연료 | O |
| insuranceType | String | 보험이름 | O |
| normalPrice | int | 일반 자차 보험 가격 | O |
| luxuryPrice | int | 고급 자차 보험 가격 | O |
| superPrice | int | 슈퍼 자차 보험 가격 | O |
| carRentCompany | String | 업체명 | O |
| reservationCount | int | 예약수 | O |
| carYear | String | 연식 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "boardList": [
    {
      "carImageUrl": "carimage.jpg",
      "carName": "아반떼",
      "fuelType": "가솔린",
      "insuranceType": "일반자차"
      "carRentCompany": "장수하자 현대렌터카 제주공항 1호점",
      "reservationCount": 0,
      "carYear": "2024",
      "normalPrice": 25100,
      "luxuryPrice": 45100,
      "superPrice": 75100
    }
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 차량)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Vehicle."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 보험별 가격 상세 검색 결과 불러오기
  
##### 설명

클라이언트로부터 차량명, 업체명, 예약수, 연식, 보험을 입력받고(클릭) 요청을 보내면 해당 차량의 상세 검색 결과를 반환합니다. 만약 존재하지 않는 차량일 경우 실패처리를 합니다. 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/search/{carName}/{rentCompany}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| carName | String | 차량명 | O |
| carRentCompany | String | 업체명 | O |

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/reservation/search/{carName}/{rentCompany}" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| carImageUrl | String | 차량 이미지 | X |
| carName | String | 차량명 | O |
| carYear | String | 연식 | O |
| reservationPeriod | String | 대여날짜 | O |
| normalPrice | int | 일반 자차 보험 가격 | O |
| luxuryPrice | int | 고급 자차 보험 가격 | O |
| superPrice | int | 슈퍼 자차 보험 가격 | O |
| brand | String | 차량 브랜드명 | O |
| grade | String | 차량 등급 | O |
| carOil | String | 연비 | O |
| fuelType | String | 연료 | O |
| capacity | String | 차량 수용인원수 | O |
| rentCompany | String | 업체 이름 | O |
| address | String | 업체 주소 | O |
| companyTelnumber | String | 업체 전화번호 | O |
| companyRule | String | 업체 영업방침 | X |


###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "carImageUrl": "carimage.jpg",
  "carName": "아반떼",
  "carYear": "2024",
  "reservationPeriod": "2024.07.05, 2024.07.06",
  "normalPrice": 25100,
  "luxuryPrice": 45100,
  "superPrice": 75100,
  "brand": "현대",
  "carOil": 15.3,
  "fuelType": "가솔린",
  "capacity": 5,
  "rentCompany": "장수하자 현대렌터카 제주공항 1호점",
  "address": "제주특별자치도 제주시 용문로 8",
  "companyTelnumber": "064-727-5680",
  "companyRule": ""
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 차량)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Vehicle."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 예약 하기

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 예약이 가능합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/regist**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 회원 아이디 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| insuranceType | String | 보험 유형 | O |
| reservationState | String | 예약 상태 | O |
| reservationStart | String | 예약 시작일 | O |
| reservationEnd | String | 예약 종료일 | O |
| companyCarCode | int | 업체 차량 코드 | O |
| price | int | 예약 가격 | O |


###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/reservation/regist" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header


| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 차량)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Vehicle."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***


<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> notice 모듈 </h2>

- url : /api/rentcar/notice 

***

#### - 공지사항 전체 게시물 리스트 불러오기

##### 설명

공지사항 페이지에서 작성일 기준 내림차순으로 공지사항 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/notice/list" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body
| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| noticeList | noticeListItem[] | 공지사항 리스트 | O |

**noticeListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| registNumber | int | 공지사항 등록 번호 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| viewCount | int | 조회수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "noticeList": [
    {
      "registNumber": 1,
      "title": "제목",
      " writerId" : "관리자",
      "writeDatetime": "24.05.02",
      "viewCount": 1
    }, ...
  ]
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 공지사항 검색 게시물 리스트 불러오기  

##### 설명

검색어를 입력받고 요청을 보내면 작성일 기준 내림차순으로 검색어에 해당하는 공지사항 게시물 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/search**  

##### Request

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| word | String | 검색어 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/notice/list/search?word=${searchWord}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body
| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| noticeList | noticeListItem[] | 공지사항 리스트 | O |

**noticeListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| registNumber | int | 공지사항 등록 번호 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| viewCount | int | 조회수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "noticeList": [
    {
      "registNumber": 1,
      "title": "제목",
      "writerId": "관리자",
      "writeDatetime": "24.05.02",
      "viewCount": 1
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

*** 

#### - 공지사항 게시물 불러오기
  
##### 설명

공지사항 페이지에서 공지사항 등록 번호를 입력받고 요청을 보내면 해당하는 공지사항 데이터를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/{registNumber}**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| registNumber | int | 공지사항 등록번호 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/notice/list/${registNumber}" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| title | String | 제목 | O |
| contents | String | 내용 | O |
| writerId | String | 작성자 | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| viewCount | int | 조회수 | O |
| imageUrl | String | 이미지 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "registNumber": ${registNumber},
  "title": "${title}",
  "contents": "${contents}",
  "writerId": "${writerId}",
  "writeDatetime": "${writeDatetime}",
  "viewCount": ${viewCount},
  "imageUrl": ${imageUrl}
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 공지사항 게시물 조회수 증가  
  
##### 설명

공지사항 게시물의 조회수를 증가합니다. 만약 증가에 실패하면 실패처리를 합니다. 인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PATCH**  
- URL : **/{registNumber}/increase-view-count**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/rentcar/notice/${registNumber}/increase-view-count" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 공지사항 작성하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 제목, 내용, 사진을 입력받고 작성에 성공하면 성공처리 합니다. 만약 작성에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/regist**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | 제목 | O |
| contents | String | 내용 | O |
| imageUrl | String | 이미지 | X |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/notice/regist \
 -H "Authorization: Bearer {JWT}" \
 -d "title={title}" \
 -d "contents={contents}" \
 -d "imageUrl={imageUrl}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 공지사항 수정하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 제목, 내용, 사진을 입력받고 수정에 성공하면 성공처리 합니다. 만약 수정에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**  
- URL : **/{registNumber}/modify**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| registNumber | int | 공지사항 등록번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | 공지사항 제목 | O |
| contents | String | 공지사항 내용 | O |
| imageUrl | String | 이미지 | X |


###### Example

```bash
curl -v -X PUT "http://localhost:4000/api/rentcar/notice/list/${registNumber}/modify" \
 -H "Authorization: Bearer {JWT}" \
 -d "title={title}" \
 -d "contents={contents}" \
 -d "imageUrl={imageUrl}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 공지사항 게시물 삭제하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 접수번호를 입력받고 요청을 보내면 해당하는 공지사항 게시물이 삭제됩니다. 만약 삭제에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/{registNumber}/delete**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| registNumber | int | 공지사항 등록번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/notice/${registNumber}/delete" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> qna 모듈 </h2>

- url : /api/rentcar/qna  

***

#### - 마이페이지에서 해당 사용자의 Q&A 전체 게시물 리스트 불러오기 

##### 설명


클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 사용자의 아이디를 입력받고 요청을 보내면 작성일 기준 내림차순으로 게시물 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**
- URL : **/mylist**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentCar/qna/mylist" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| boardMyList | boardMyListItem[] | Q&A 게시물 리스트 | O |

**boardMyListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 공개여부 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디</br>(첫글자를 제외한 나머지 문자는 *) | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| category | String | 유형 | O |
| viewCount | int | 조회수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "boardMyList": [
    {
      "receptionNumber": 1,
      "status": "비공개",
      "title": "테스트1",
      "writerId": "j******",
      "writeDatetime": "24.05.02",
      "category": "문의",
      "viewCount": 0
    }, ...
  ]
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 전체 게시물 리스트 불러오기  

##### 설명

작성일 기준 내림차순으로 Q&A 전체 게시물 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/qna/list" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| qnaList | QnaListItem[] | Q&A 게시물 리스트 | O |

**QnaListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 공개여부 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디</br>(첫글자를 제외한 나머지 문자는 *) | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| category | String | 유형 | O |
| viewCount | int | 조회수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "boardList": [
    {
      "receptionNumber": 1,
      "status": false,
      "title": "테스트1",
      "writerId": "j******",
      "writeDatetime": "24.05.02",
      "category": "문의",
      "viewCount": 0
    }, ...
  ]
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 검색 게시물 리스트 불러오기  
  
##### 설명

검색어를 입력받고 요청을 보내면 작성일 기준 내림차순으로 검색어에 해당하는 Q&A 게시물 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/search**  

##### Request

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| searchWord | String | 검색어 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/qna/list/search?word=${searchWord}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| boardList | BoardListItem[] | Q&A 게시물 리스트 | O |

**BoardListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 공개여부 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디</br>(첫글자를 제외한 나머지 문자는 *) | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |
| category | String | 유형 | O |
| viewCount | int | 조회수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "boardList": [
    {
      "receptionNumber": 1,
      "status": false,
      "title": "테스트1",
      "writerId": "j******",
      "writeDatetime": "24.05.02",
      "category": "문의",
      "viewCount": 0
    }, ...
  ]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 불러오기  
  
##### 설명

Q&A 게시물 데이터를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 데이터베이스 에러, 데이터베이스 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/{receptionNumber}**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/qna/list/${receptionNumber}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 상태 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디 | O |
| writeDatetime | String | 작성일</br>(yyyy.mm.dd 형태) | O |
| viewCount | int | 조회수 | O |
| contents | String | 내용 | O |
| comment | String | 답글 내용 | X |
| imageUrl | String | 이미지 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "receptionNumber": ${receptionNumber},
  "status": ${status},
  "title": "${title}",
  "writerId": "${writerId}",
  "writeDatetime": "${writeDatetime}",
  "viewCount": ${viewCount},
  "contents": "${contents}",
  "comment": "${comment}",
  "imageUrl": "${imageUrl}"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 작성  

##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 제목, 내용을 입력받고 작성에 성공하면 성공처리를 합니다. 만약 작성에 실패하면 실패처리 됩니다. 인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **POST**
- URL : **/regist**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | Q&A 제목 | O |
| contents | String | Q&A 내용 | O |
| category | String | Q&A 유형 | O |
| imageUrl | String | 이미지 | X |
| publicState | Boolean | 공개여부 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/qna/regist" \
 -H "Authorization: Bearer {JWT}" \
 -d "title={title}" \
 -d "contents={contents}"\
 -d "imageUrl={imageUrl}" \
 -d "category={category}" \
 -d "publicState={publicState}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 조회수 증가  
  
##### 설명

Q&A 게시물의 조회수를 증가합니다. 만약 증가에 실패하면 실패처리를 합니다. 데이터베이스 에러가 발생할 수 있습니다.

- method : **PATCH**  
- URL : **/{receptionNumber}/increase-view-count**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/rentcar/qna/${receptionNumber}/increase-view-count" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 삭제  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 접수번호를 입력받고 요청을 보내면 해당하는 Q&A 게시물이 삭제됩니다. 만약 삭제에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/{receptionNumber}/delete**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/qna/${receptionNumber}/delete" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{ 
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 수정  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 접수 번호, 제목, 내용을 입력받고 수정에 성공하면 성공처리를 합니다. 만약 수정에 실패하면 실패처리 됩니다. 인가 실패, 인증실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**  
- URL : **/{receptionNumber}/modify**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 수정할 접수 번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | Q&A 제목 | O |
| contents | String | Q&A 내용 | O |
| category | String | Q&A 유형 | O |
| imageUrl | String | 이미지 | X |
| publicState | Boolean | Q&A 공개여부 | O |

###### Example

```bash
curl -v -X PUT "http://localhost:4000/api/rentcar/qna/${receptionNumber}/modify" \
 -H "Authorization: Bearer {JWT}" \
 -d "title={title}" \
 -d "contents={contents} \
 -d "category={category} \
 -d "imageUrl={imageUrl} \
 -d "publicState={publicState} 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (답변 완료된 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "WC",
  "message": "Written Comment."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - Q&A 게시물 답글 작성  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 접수번호와 답글 내용을 입력받고 요청을 보내면 해당하는 Q&A 게시물의 답글이 작성됩니다. 만약 증가에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/{receptionNumber}/comment**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| comment | String | 답글 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/qna/${receptionNumber}/comment" \
 -H "Authorization: Bearer {JWT}" \
 -d "comment={commnet}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (이미 작성된 답글)**
```bash
HTTP/1.1 400 Bad Request
{
  "code": "WC",
  "message": "Written Comment."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'> company 모듈 </h2>

- url : /api/rentcar/company

***

#### - 업체 전체 리스트 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 등록일 기준 내림차순으로 업체 정보 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/company/list" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| companyList | companyListItem[] | 업체 정보 리스트 | O |

**companyListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | int | 업체 번호 | O |
| rentCompany | String | 업체명 | O |
| address | String | 주소 | O |
| owner | String | 담당자 | O |
| companyTelnumber | String | 연락처 | O |
| registDate | String | 등록일</br>(yy.mm.dd 형태) | O |
| companyRule | String | 업체 방침 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "companyList": [
    {
      "companyCode": 101,
      "rentCompany": "A 제주공항점",
      "address": "제주시",
      "owner": "김민철",
      "companyTelnumber": "010-1234-5678",
      "registDate": "24.02.04",
      "companyRule": "영업시간 : 08:00 ~ 18:00"
    }, ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 업체 상세 정보 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 업체 상세 정보를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/{companyCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/company/list/${companyCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| companyCode | int | 업체 번호 | O |
| rentCompany | String | 업체명 | O |
| address | String | 주소 | O |
| owner | String | 담당자 | O |
| companyTelnumber | String | 연락처 | O |
| registDate | String | 등록일</br>(yy.mm.dd 형태) | O |
| companyRule | String | 업체 방침 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "companyCode": 101,
  "rentCompany": "A 제주공항점",
  "address": "제주시",
  "owner": "김민철",
  "companyTelnumber": "010-1234-5678",
  "registDate": "24.02.04",
  "companyRule": "영업시간 : 08:00 ~ 18:00"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (존재하지 않는 업체)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Company."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 업체 검색 리스트 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 검색어를 입력받고 요청을 보내면 등록일 기준 내림차순으로 업체명 검색 시 해당 정보 리스트를 반환합니다. 만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list/search**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4000/api/rentcar/company/list/search?word=${searchWord}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| companyList | CompanyListItem[] | 업체 정보 리스트 | O |

**CompanyListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | int | 업체 번호 | O |
| rentCompany | String | 업체명 | O |
| address | String | 주소 | O |
| owner | String | 담당자 | O |
| companyTelnumber | String | 연락처 | O |
| registDate | String | 등록일</br>(yy.mm.dd 형태) | O |
| companyRule | String | 업체 방침 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "companyList": [
    {
      "companyCode": 101,
      "rentCompany": "A 제주공항점",
      "address": "제주시",
      "owner": "김민철",
      "companyTelnumber": "010-1234-5678",
      "registDate": "24.02.04",
      "companyRule": "영업시간 : 08:00 ~ 18:00"
    }, ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 업체 등록하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 업체번호, 업체명, 주소, 담당자 이름, 연락처, 업체방침을 입력받고 등록에 성공하면 성공처리 합니다. 만약 등록에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **POST**  
- URL : **/regist**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | Integer | 업체번호 | O |
| rentCompany | String | 업체명 | O |
| address | String | 주소 | O |
| owner | String | 담당자 | O |
| companyTelnumber | String | 연락처 | O |
| companyRule | String | 영업방침 | X |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/rentcar/company/regist" \
 -H "Authorization: Bearer {JWT}" \
 -d "rentCompany={rentCompany}" \
 -d "address={address}" \
 -d "owner={owner}" \
 -d "companyTelnumber={companyTelnumber}" \
 -d "companyRule={companyRule}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (이미 등록된 업체)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "RC",
  "message": "Registed Company."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 업체 수정하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 업체코드, 업체명, 주소, 담당자 이름, 연락처, 업체방침을 입력받고 수정에 성공하면 성공처리 합니다. 만약 수정에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**  
- URL : **/{companyCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | int | 수정할 업체 번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | Integer | 업체번호 | O |
| rentCompany | String | 업체명 | O |
| address | String | 주소 | O |
| owner | String | 담당자 | O |
| companyTelnumber | String | 연락처 | O |
| companyRule | String | 영업방침 | X |

###### Example

```bash
curl -v -X PUT "http://localhost:4000/api/rentcar/company/${companyCode}" \
 -H "Authorization: Bearer {JWT}"
 -d "companyCode={companyCode}" \
 -d "rentCompany={rentCompany}" 
 -d "address={address}
 -d "owner={owner}
 -d "companyTelnumber={companyTelnumber}
 -d "companyRule={companyRule}
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 업체)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Company."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 업체 삭제하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 업체번호를 입력받고 요청을 보내면 해당하는 업체 정보가 삭제됩니다. 만약 삭제에 실패하면 실패처리를 합니다. 인가 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/list/{companyCode}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| companyCode | int | 수정할 업체 번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/rentcar/company/${companyCode}" \
 -H "Authorization: Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| contentType | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
contentType: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 업체)**
```bash
HTTP/1.1 400 Bad Request
contentType: application/json;charset=UTF-8
{
  "code": "NC",
  "message": "No Exist Company."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
contentType: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
contentType: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```