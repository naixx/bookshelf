Book discovery application 📚
-----------------------------

Assignment
----------

**Write a Android application for book discovery using an API that we provide**

-   User must be able to create an account and log in.

-   User must be able to discover and search for books

-   Functional UI/UX design is needed. You are not required to create a unique design, however, do follow best practices to make the project as functional as possible.

-   After user initially logging in, he must not login again as he already received a token

-   You can take inspiration from this Bookshelf app [https://bookshelf.lol](https://www.google.com/url?q=https://bookshelf.lol/&source=gmail-html&ust=1636788504815000&usg=AOvVaw0BS_1VqdbRdiYXI6l9noRf) (especially the discover page)

-   When the app is loaded, a background task/process needs to be started, that monitors the user's clicks/touches grouped by each component or screen

**Technical requirements:**

1.  Works on Android --- we need an APK

1.  SignUp/Login screen - where user can login/register

1.  Books screen - Where books from the api are displayed. The screen should have a search bar at the top where when user types results of his query should be displayed. *Hint:* Use the `/books?q=".."` api call While results are loading, the user should see a loading indicator

1.  Individual book screen. When the user taps on a book, he gets taken to an individual book screen where he can read the full synopsis of the book

**Nice to have:**
-----------------

-   Clean code. Write as if somebody will look over your code (because somebody will)

-   Kotlin

Evaluation
----------

We look for:

1.  The way you structure your test code and how you write it

1.  Your train of thought

1.  Your testing skills

1.  Your decision-making

1.  Attention to details

**Submission:**
---------------

Provide a public GitHub repo with the project. Provide a [readme.md](https://www.google.com/url?q=http://readme.md&source=gmail-html&ust=1636788504815000&usg=AOvVaw0wlATjTGP3FG_ZC5n8LvFD) on how to build locally and also provide the final APK.

Deadline
--------

It has to be submitted within 24hours since the moment you received this assignment.

API Documentation
-----------------

### `API_URL: [http://104.248.26.141:3000/api](https://www.google.com/url?q=http://104.248.26.141:3000/api&source=gmail-html&ust=1636788504815000&usg=AOvVaw2TrE22UHqaJ36ioqkQjbd9)`

### **Auth**:

-   Register. URL: `POST {{API_URL}}/auth/register`

```
BODY: {
  username: 'string',
  password: 'string'
}

RESPONSES:
200 (OK): {
           "user": {
		         "id": "5cbbc2c8-e43e-4edb-afd9-f0c96f680d14",
		         "username": "test2",
		         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYmJjMmM4LWU0M2UtNGVkYi1hZmQ5LWYwYzk2ZjY4MGQxNCIsInVzZXJuYW1lIjoidGVzdDIiLCJpYXQiOjE2MTcyMDY1MDEsImV4cCI6MTYyMjM5MDUwMX0.RAheTgvk3G54HpDirl2VPKB_KemAwG2P6I8Z5au-0D0"
            }
           }

400 (BAD_REQUEST)
{
  "message": "password can't be blank" // or other invalid reasons
}
```

-   Login. URL: `POST {{API_URL}}/auth/login`

```
BODY: {
  username: 'string',
  password: 'string'
}

RESPONSES:
200 (OK): {
           "user": {
		         "id": "5cbbc2c8-e43e-4edb-afd9-f0c96f680d14",
		         "username": "test2",
		         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYmJjMmM4LWU0M2UtNGVkYi1hZmQ5LWYwYzk2ZjY4MGQxNCIsInVzZXJuYW1lIjoidGVzdDIiLCJpYXQiOjE2MTcyMDY1MDEsImV4cCI6MTYyMjM5MDUwMX0.RAheTgvk3G54HpDirl2VPKB_KemAwG2P6I8Z5au-0D0"
            }
           }

400 (BAD_REQUEST)
{
  "message": "username or password is invalid" // or other invalid reasons
}
```

### Books

**IMPORTANT!:** These API calls are protected and to access them, you need to obtain a token from either the **login** or **register** api calls and give your request an authentication header like so:

```
headers: {
                          // token obtained from register/login
 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYmJjMmM4LWU0M2UtNGVkYi1hZmQ5LWYwYzk2ZjY4MGQxNCIsInVzZXJuYW1lIjoidGVzdDIiLCJpYXQiOjE2MTcyMDY1MDEsImV4cCI6MTYyMjM5MDUwMX0.RAheTgvk3G54HpDirl2VPKB_KemAwG2P6I8Z5au-0D0'
}
```

-   **Get books**. `GET {{API_URL}}/books`

    If called as is, the api call will return 10 random books

    This api call can be passed `q` as a way to filter books.

    Example: `GET {{API_URL}/books?q=harry` ⇒ Will most likely result in Harry Potter books

    Response:

```
200: {
    "books": [
        {
            "title": 1984,
            "author": "George Orwell",
            "coverImageUrl": "https://images-na.ssl-images-amazon.com/images/I/31lWUHDG7uL._SX282_BO1,204,203,200_.jpg",
            "id": "451524934",
            "pageCount": 328,
            "publisher": "Signet Classics",
            "synopsis": "Winston Smith toes the Party line, rewriting history to satisfy the demands of the Ministry of Truth. With each lie he writes, Winston grows to hate the Party that seeks power for its own sake and persecutes those who dare to commit thought crimes. But as he starts to think for himself, Winston can't escape the fact that Big Brother is always watching...\n\nA startling and haunting vision of the world, 1984 is so powerful that it is completely convincing from start to finish. No one can deny the influence of this novel, its hold on the imaginations of multiple generations of readers, or the resiliency of its admonitions---a legacy that seems only to grow with the passage of time."
        },
        {
            "title": "A Bear Called Paddington",
            "author": "Michael Bond",
            "coverImageUrl": "https://images-na.ssl-images-amazon.com/images/I/51clgmTURAL._SX321_BO1,204,203,200_.jpg",
            "id": "62312189",
            "pageCount": 176,
            "publisher": "HarperCollins",
            "synopsis": "Paddington Bear had just traveled all the way from Peru when the Brown family first met him in Paddington Station. Since then, their lives have never been quite the same . . . for ordinary things become extraordinary when Paddington is involved."
        }
     ]
}

200 {
      "books": [] //if no books were found
    }
```

-   Get book by id . `GET {{API_URL}}/book/:id`

Example: `GET {{API_URL}/books/451524934`

Response:

```
200 {"book": {
            "title": 1984,
            "author": "George Orwell",
            "coverImageUrl": "https://images-na.ssl-images-amazon.com/images/I/31lWUHDG7uL._SX282_BO1,204,203,200_.jpg",
            "id": "451524934",
            "pageCount": 328,
            "publisher": "Signet Classics",
            "synopsis": "Winston Smith toes the Party line, rewriting history to satisfy the demands of the Ministry of Truth. With each lie he writes, Winston grows to hate the Party that seeks power for its own sake and persecutes those who dare to commit thought crimes. But as he starts to think for himself, Winston can't escape the fact that Big Brother is always watching...\n\nA startling and haunting vision of the world, 1984 is so powerful that it is completely convincing from start to finish. No one can deny the influence of this novel, its hold on the imaginations of multiple generations of readers, or the resiliency of its admonitions---a legacy that seems only to grow with the passage of time."
        }
     }

404  {
       "message": "No book was found with the id of 451524934"
     }
```
