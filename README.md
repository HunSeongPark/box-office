# box-office-MVVM
box office app using MVVM pattern.

-----`set profile, movies` ------------------`detail` ------------------------`like`  --------------------------
<img src="https://user-images.githubusercontent.com/71416677/134463518-a43464f5-07c8-4859-b588-d4337deeeb19.gif" width="250" height="400"/>
<img src="https://user-images.githubusercontent.com/71416677/134463546-014f8fe1-a349-47c4-9261-448d6bb8d2a3.gif" width="250" height="400"/>
<img src="https://user-images.githubusercontent.com/71416677/134463663-38f6d88d-5292-48e0-83ea-323b61cc932b.gif" width="250" height="400"/>     

## Architecture Pattern
MVVM 

![final-architecture](https://user-images.githubusercontent.com/71416677/132950781-3b8c1373-825b-4685-a900-de84f4e5f062.png)  

## Details
* `set profile`    
install app first, set your profile.      
With Shared Preference(Local Data), your profile name keep until delete the app.

* `movies`  
using Retrofit(Network Data). display Using Recycler View.      
using mock data (![MockAPI](https://mockapi.io/))       
movie poster designed by (![이상](https://github.com/2snng))      

* `detail`  
for study fragment - Activity transition animation and putParcelable method.    


* `like`  
Using Room(Local Data), all like movies keep until delete the app.    


## Used Libraries
* Glide
* OkHttp3
* Retrofit2
* Koin
* Room
* Shared Preference
* Coroutine
