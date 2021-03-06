[tags]: <> (Python, Java, SQL, Django, Android, REST API, BeaufitulSoup, SQLite)
## Wat Plan   
*Full stack* project of my university's departament of cybernetics schedule, containing an android application and independent web api managing the used data.

##### You can download the android app's apk at <https://watplan.eu.pythonanywhere.com/home>


How does it work?
---
  * ### The mobile app itself
    It is an *Andoird* application, written in *Java*, which obtains data from the web service, and saves it to local *SQL* storage using *SQLite* database, so that it is possible to acces previously visited schedules offline. Each time any schelude of a group is requested to open, app checks if its local version matches the one stored in the service's database.   
    
<table style="width:100%">
<tr>
<th>First Startup</th>
<th>Changing Filters</th> 
</tr>
<tr>
<td valign="top">
<img src="preview/watplanstart.gif" width="285" height="600">
</td>
<td valign="top">
<img src="preview/watplanfeatures.gif" width="285" height="600">
</tr>
</table>

  * ### Obtaining data   
    The data is scraped by a multithreaded *Python* program, which gets the contents of the departament's service and parses 
    it using *Beautiful Soup* library, in order to post the data to the web service described below afterwards.
    
  * ### Transfering data to the app
    To enable WAT Plan android app to get the desired data, I created a simple *Django* *REST API*.   
    The service keeps track of any changes in the schedules posted to itself by scraping bots, so that WAT Plan is always up to date.
    
