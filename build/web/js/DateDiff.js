/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function isValidDate(dateStr) {// Checks for the following valid date formats:// MM/DD/YY   MM/DD/YYYY   MM-DD-YY   MM-DD-YYYY
    var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/; // requires 4 digit year
    var matchArray = dateStr.match(datePat); // is the format ok?
    if (matchArray == null) {
        alert(dateStr + " Date is not in a valid format.");
        return false;
    }
    month = matchArray[1]; // parse date into variables
    day = matchArray[3];
    year = matchArray[4];
    if (month < 1 || month > 12) { // check month range
       alert("Month must be between 1 and 12.");
       return false;
   }
   if (day < 1 || day > 31) {
       alert("Day must be between 1 and 31.");
       return false;
   }
   if ((month==4 || month==6 || month==9 || month==11) && day==31) {
       alert("Month "+month+" doesn't have 31 days!")
       return false;
   }
   if (month == 2) { // check for february 29th
      var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
      if (day>29 || (day==29 && !isleap)) {
          alert("February " + year + " doesn't have " + day + " days!");
          return false;
      }
  }
  return true;
}

function isValidTime(timeStr) {
// Checks if time is in HH:MM:SS AM/PM format.
// The seconds and AM/PM are optional.
    var timePat = /^(\d{1,2}):(\d{2})(:(\d{2}))?(\s?(AM|am|PM|pm))?$/;
    var matchArray = timeStr.match(timePat);
    if (matchArray == null) {
        alert("Time is not in a valid format.");
        return false;
    }
    hour = matchArray[1];
    minute = matchArray[2];
    second = matchArray[4];
    ampm = matchArray[6];
    if (second=="") {
        second = null;
    }
    if (ampm=="") {
        ampm = null
    }
    if (hour < 0  || hour > 23) {
        alert("Hour must be between 1 and 12. (or 0 and 23 for military time)");
        return false;
    }
    if (hour <= 12 && ampm == null) {
//        if (confirm("Please indicate which time format you are using.  OK = Standard Time, CANCEL = Military Time")) {
//            alert("You must specify AM or PM.");
//            return false;
//        }
    }
    if  (hour > 12 && ampm != null) {
        alert("You can't specify AM or PM for military time.");
        return false;
    }
    if (minute < 0 || minute > 59) {
        alert ("Minute must be between 0 and 59.");
        return false;
    }
    if (second != null && (second < 0 || second > 59)) {
        alert ("Second must be between 0 and 59.");
        return false;
    }
    return true;
}

function dateDiffDDTT(dateJsp1, dateJsp2, firsttime, secondtime, DDid, TTid) {

    var firstdate  = parseMMDDYY(dateJsp1);
    var seconddate = parseMMDDYY(dateJsp2);

    date1 = new Date();
    date2 = new Date();
    diff  = new Date();
    if (isValidDate(firstdate) && isValidTime(firsttime)) { // Validates first date
        date1temp = new Date(firstdate + " " + firsttime);
        date1.setTime(date1temp.getTime());
    }else
        return false; // otherwise exits

    if (isValidDate(seconddate) && isValidTime(secondtime)) { // Validates second date
        date2temp = new Date(seconddate + " " + secondtime);
        date2.setTime(date2temp.getTime());
    }else
        return false; // otherwise exits

    // sets difference date to difference of first date and second date
    diff.setTime(Math.abs(date1.getTime() - date2.getTime()));
    timediff = diff.getTime();
    weeks = Math.floor(timediff / (1000 * 60 * 60 * 24 * 7));
    timediff -= weeks * (1000 * 60 * 60 * 24 * 7);
    days = Math.floor(timediff / (1000 * 60 * 60 * 24));
    timediff -= days * (1000 * 60 * 60 * 24);
    hours = Math.floor(timediff / (1000 * 60 * 60));
    timediff -= hours * (1000 * 60 * 60);
    mins = Math.floor(timediff / (1000 * 60));
    timediff -= mins * (1000 * 60);
    secs = Math.floor(timediff / 1000);
    timediff -= secs * 1000;

    days = days + (weeks * 7);

    document.getElementById(DDid).value = days;
    document.getElementById(TTid).value = hours;
    //document.getElementById(TTid).value = hours + "." + mins;
    return true;
}

function parseMMDDYY(s1){

	var d1 = s1.substring(0,s1.indexOf('/'));

	var v1 = s1.substring(s1.indexOf('/')+1,s1.length);

	var m1 = v1.substring(0,v1.indexOf('/'));

	v1 = v1.substring(v1.indexOf('/')+1,v1.length);

	var y1 = v1;

	var mdy = m1 + "/" + d1 + "/" + y1;

	return mdy;
}

function dateDiffYYMMDD(dateJsp1, dateJsp2, YYid, MMid, DDid){
    
    var firstdate  = parseMMDDYY(dateJsp1);
    var seconddate = parseMMDDYY(dateJsp2);

    if ( isValidDate(firstdate) && isValidDate(seconddate) )
    {
        var date1 = new Date(firstdate);
        var date2 = new Date(seconddate);

        var milsecDiff=Math.abs(date1-date2); // You Will get Diffrence in Milliseconds
        var Year=parseInt(Math.abs(milsecDiff/ ( 1000 * 3600 * 24 * 365)));
        var Month=parseInt(Math.abs((milsecDiff - (Year * ( 1000 * 3600 * 24 * 365) ) ) / ( 1000 * 3600 * 24 * 30) ));
        var Day=parseInt(Math.abs((milsecDiff -( (Year * ( 1000 * 3600 * 24 * 365))+ ( Month * ( 1000 * 3600 * 24 * 30) ) ) ) / ( 1000 * 3600 * 24) ))

        document.getElementById(YYid).value = Year;
        document.getElementById(MMid).value = Month;
        document.getElementById(DDid).value = Day;
        return true;
    }
    else{
        return false;
    }
}
function dateDiff(dateJsp1, dateJsp2, indicater){
    var firstdate  = parseMMDDYY(dateJsp1);
    var seconddate = parseMMDDYY(dateJsp2);
    var returnVal = false;
    if ( isValidDate(firstdate) && isValidDate(seconddate) )
    {
        var date1 = new Date(firstdate);
        var date2 = new Date(seconddate);

        var milsecDiff=Math.abs(date1-date2); // You Will get Diffrence in Milliseconds
        var Year=parseInt(Math.abs(milsecDiff/ ( 1000 * 3600 * 24 * 365)));
        var Month=parseInt(Math.abs((milsecDiff - (Year * ( 1000 * 3600 * 24 * 365) ) ) / ( 1000 * 3600 * 24 * 30) ));
        var Day=parseInt(Math.abs((milsecDiff -( (Year * ( 1000 * 3600 * 24 * 365))+ ( Month * ( 1000 * 3600 * 24 * 30) ) ) ) / ( 1000 * 3600 * 24) ))
        if(indicater == 'Y' && (Year == 0 ||(Year == 1 &&Month == 0 &&Day==0))){
             returnVal= true;
         }else if(indicater == 'Q' && Year == 0 && ( Month < 3 ||(Month == 3 &&Day==0))){
             returnVal= true;
         }else if(indicater == 'M' && Year == 0 && ( Month < 1 ||(Month == 1 &&Day==0))){
             returnVal= true;
         }else if(indicater == 'W' && Year == 0 && Month == 0 && Day<=6){
             returnVal= true;
         }else if(indicater == 'D' && Year == 0 && Month == 0 && Day==0){
             returnVal= true;
         }else if(indicater == 'A' ){
             returnVal= true;
         }else if(indicater == '' && Year == 0 && Month == 0 && Day<=6){
             returnVal= true;
         }else{
             returnVal= false;
         }
    }
    else{
        returnVal= false;
    }
    return returnVal;
}
function fnAddDate( vCurrDate, type, iNum ){

    var vCurrYYYY = vCurrDate.substr(6, 4);
    var vCurrMM   = vCurrDate.substr(3, 2);
    var vCurrDD   = vCurrDate.substr(0, 2);

    var vCurrDt = new Date(vCurrYYYY, Number(vCurrMM)-1, vCurrDD);

    var vNextDt = new Date();

    if( type == 'DD' )
        vNextDt = new Date(vCurrDt).addDays(Number(iNum));
    else if ( type == 'MM' )
        vNextDt = new Date(vCurrDt).addMonths(Number(iNum));
    else if ( type == 'YYYY' )
        vNextDt = new Date(vCurrDt).addYears(Number(iNum));

    var vNextYYYY = vNextDt.getFullYear();
    var vNextMM   = (vNextDt.getMonth() + 1);
    var vNextDD   = vNextDt.getDate();

    if(vNextMM<10) vNextMM = '0'+vNextMM;
    if(vNextDD<10) vNextDD = '0'+vNextDD;

    var iNextDt = vNextDD+'/'+vNextMM+'/'+vNextYYYY;

    return iNextDt;
}


