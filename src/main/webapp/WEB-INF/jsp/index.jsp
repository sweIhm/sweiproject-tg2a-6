<!DOCTYPE html>
<html ng-app="ActivityMeterApp">
<head>
<title>ActiviTracker</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.1.0/ui-bootstrap-tpls.js"></script>
	
<link rel="shortcut icon" type="image/png" href="favicon.png">

<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="style.css" />


<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet"> 



<script>
var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
var months = ["January","February","March","April","May","Juni","July","August","Septemper","October","November","December",];

var geocoder;
var map;
var map2;

var bounds;
var bounds2;

function initializeMaps() {

	var centerData;
	var centerData2;
	var address = "Lothstrasse 64, 80335 Muenchen";
	var address2 = "1 Grand Avenue, San Luis Obispo, CA 93407";
	geocoder = new google.maps.Geocoder();

	geocoder.geocode({
		'address': address
	}, function(results, status) {
		if (status == 'OK') {
			centerData = results[0].geometry.location;
		} else {
			alert('Geocode was not successful for the following reason: ' + status);
		}
	});
	geocoder.geocode({
		'address': address2
	}, function(results, status) {
		if (status == 'OK') {
			centerData2 = results[0].geometry.location;
		} else {
			alert('Geocode was not successful for the following reason: ' + status);
		}
	});
      
	var mapOptions = {
		zoom: 11,
		maxZoom: 14,
		center: centerData,
		disableDefaultUI: true,
		styles: [
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "elementType": "labels.icon",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#bdbdbd"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#ffffff"
      }
    ]
  },
  {
    "featureType": "road.arterial",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dadada"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#c9c9c9"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  }
]
	}
	var mapOptions2 = {
		zoom: 9,
		maxZoom: 14,
		center: centerData2,
		disableDefaultUI: true,
		styles: [
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "elementType": "labels.icon",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#bdbdbd"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#ffffff"
      }
    ]
  },
  {
    "featureType": "road.arterial",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dadada"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#c9c9c9"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  }
]
	}
	
	map = new google.maps.Map(document.getElementById('map'), mapOptions);
	map2 = new google.maps.Map(document.getElementById('map2'), mapOptions2);
	
	//currCenter = map.getCenter();
	//currCenter2 = map2.getCenter();
	
	window.onresize = function(event) {
  		google.maps.event.trigger(map, 'resize');
  		map.fitBounds(bounds);
		map.panToBounds(bounds);
		google.maps.event.trigger(map2, 'resize');
		map2.fitBounds(bounds2);
		map2.panToBounds(bounds2);
	};
	
	console.log("maps init finished");
}
function reverseOrder() {
		
}


</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBsrHbQSVfjOxEgQ7btb-1yM6PpHX-gmEM&callback=initializeMaps" defer></script>

<script>


var app = angular.module('ActivityMeterApp', ['ui.bootstrap']);

//$qProvider.errorOnUnhandledRejections(false);

    
function loadActivities ($scope, $http){
		$http({
       		 method : 'GET',
             url: 'rest/activity'
                    
    		}).then(function (response) {
     			 $scope.activities = response.data;  			 
  		});
  		
  		loadMUASActivitiesForGMap($scope, $http);
  		loadCalPolyActivitiesForGMap($scope, $http);
			
}

function loadReportedActivities ($scope, $http){
		$http({
       		 method : 'GET',
             url: 'rest/report/0'
                    
    		}).then(function (response) {
     			 $scope.reportedActivities = response.data;  			 
  		});
}




function loadMUASActivitiesForGMap ($scope, $http){

		bounds = new google.maps.LatLngBounds();

		$http({
       		 method : 'GET',
             url: 'rest/mapdata/MUAS'
                    
    		}).then(function (response) {
     			 $scope.HMActivitiesForGMap = response.data;    
     			   		  		
  		    angular.forEach($scope.HMActivitiesForGMap,function(value,index){
                console.log(value.zipcode);
      			geocoder.geocode({
          			'address': value.zipcode + " Germany"
      			}, function(results, status) {
          			if (status == 'OK') {
              			
              			var marker = new google.maps.Marker({
                  			map: map,
                  			position: results[0].geometry.location,
                  			icon: 'flag.png'
              			});
              			
              			var loc = new google.maps.LatLng(marker.position.lat(), marker.position.lng());
              			bounds.extend(loc);
              
						map.fitBounds(bounds);
						map.panToBounds(bounds);
              
                		marker.addListener('click', function() {
							$scope.show(value);
  						});
              		} else {
              			alert('Geocode was not successful for the following reason: ' + status);
          			}
      			}); 
            })	  			 
  		});
}

function loadCalPolyActivitiesForGMap ($scope, $http){

		bounds2 = new google.maps.LatLngBounds();

		$http({
       		 method : 'GET',
             url: 'rest/mapdata/CalPoly'
                    
    		}).then(function (response) {
     			 $scope.CalPolyActivitiesForGMap = response.data;    
     			   		  		
  		    angular.forEach($scope.CalPolyActivitiesForGMap,function(value,index){
                console.log(value.zipcode);
      			geocoder.geocode({
          			'address': value.zipcode + " USA"
      			}, function(results, status) {
          			if (status == 'OK') {

              			var marker = new google.maps.Marker({
                  			map: map2,
                  			position: results[0].geometry.location,
                  			icon: 'flag.png'
              			});
              			
              			var loc = new google.maps.LatLng(marker.position.lat(), marker.position.lng());
              			bounds2.extend(loc);
              			
              			map2.fitBounds(bounds2);
						map2.panToBounds(bounds2);
              
                		marker.addListener('click', function() {
							$scope.show(value);
  						});
              		} else {
              			alert('Geocode was not successful for the following reason: ' + status);
          			}
      			}); 
            })	  			 
  		});
}


app.controller('MenuCtrl', function ($scope, $http, $dialog) {

	$scope.getView = function() {
		var strParam = window.location.search.substring(1);
		var arrParam = strParam.split("&");
		var objParam = {};
		for(var i = 0; i < arrParam.length; i++) {
			var splitParam = arrParam[i].split("=");
			objParam[splitParam[0]] = splitParam[1];
		}
		
		if(objParam.map == "muas")
			this.showMap1();
		else if(objParam.map == "calpoly")
			this.showMap2();
		
	}

	$scope.showMap1 = function() {
		document.getElementById("map2").style.display = "none";
		document.getElementById("menu-item-map2").classList.remove("current-menu-item");
		document.getElementById("activities-table").style.display = "none";
		document.getElementById("menu-item-activities-table").classList.remove("current-menu-item");
		document.getElementById("map").style.display = "block";
		document.getElementById("menu-item-map").classList.add("current-menu-item");
		google.maps.event.trigger(map, 'resize');
		map.fitBounds(bounds);
		map.panToBounds(bounds);	}
	
	$scope.showMap2 = function() {
		document.getElementById("map").style.display = "none";
		document.getElementById("menu-item-map").classList.remove("current-menu-item");
		document.getElementById("activities-table").style.display = "none";
		document.getElementById("menu-item-activities-table").classList.remove("current-menu-item");
		document.getElementById("map2").style.display = "block";
		document.getElementById("menu-item-map2").classList.add("current-menu-item");
		google.maps.event.trigger(map2, 'resize');
		map2.fitBounds(bounds2);
		map2.panToBounds(bounds2);
	}
	
	$scope.showList = function() {
		document.getElementById("map2").style.display = "none";
		document.getElementById("menu-item-map2").classList.remove("current-menu-item");
		document.getElementById("map").style.display = "none";
		document.getElementById("menu-item-map").classList.remove("current-menu-item");
		document.getElementById("activities-table").style.display = "block";
		document.getElementById("menu-item-activities-table").classList.add("current-menu-item");
	}


});

  	
app.controller('ActivityCtrl', function ($scope, $http, $dialog) {
  	
  	loadActivities($scope, $http);
  	loadReportedActivities($scope, $http);
  	
  	$scope.getActivities = function(){
		return $scope.activities;
  	};
		  
  	var addDialogOptions = {
    	controller: 'PostCtrl',
    	templateUrl: './activityAdd.html'
  	};
  	
  	$scope.add = function(activity){
    	$dialog.dialog(angular.extend(addDialogOptions, {})).open().then(function (){
    	    loadActivities($scope, $http);
        }) ;
  	};
  	
  	var editDialogOptions = {
	    controller: 'EditActivityCtrl',
	    templateUrl: './activityEdit.html',
	};
  	$scope.edit = function(activity){
   	 	var activityToEdit = activity;
    	$dialog.dialog(angular.extend(editDialogOptions, {resolve: {activity: angular.copy(activityToEdit)}})).open().then(function (){
    	    loadActivities($scope, $http);
        }) ;
  	};
		
	$scope.delete = function(activity) {
		var deleteRequest = {
			method : 'DELETE',
			url: 'rest/delete/' + activity.id
		};
		
  		$http(deleteRequest).then(function() {
			loadActivities($scope, $http);
			loadReportedActivities($scope, $http);
		});
  		//todo handle error
	};

	var showDialogOptions = {
	    controller: 'ShowActivityCtrl',
	    templateUrl: './activityShow.html',
	};

	$scope.show = function(activity) {
		$dialog.dialog(angular.extend(showDialogOptions, {resolve: {activity: angular.copy(activity)}})).open().then(function (){
    	    loadActivities($scope, $http);
        }) ;
	}

	<% if(request.getAttribute("login") != null && request.getAttribute("login").equals((Boolean)true)) { %>
	$scope.logout = function() {
		var deleteRequest = 
						{
							method : 'DELETE',
							url: 'rest/admin' ,
						}  
			
						$http(deleteRequest).then(function (response) {window.location.href='/admin?logout=true';});
	}
	<% } %>
});


app.controller('EditActivityCtrl', function ($scope, $http, activity, dialog) {
  
	$scope.activity = activity;
  	$scope.save = function($activity) {
  	    var putRequest = {
    	method : 'PUT',
       	url: 'rest/activity/' + $scope.activity.id,
       	data: {
  				text: $scope.activity.text,
  				tags: $scope.activity.tags,
  				title: $scope.activity.title
			  }
		}  
		
  		$http(putRequest).then(function (response) {
  		    $scope.activities = response.data;
  		}).then(function () {
			//todo handle error
			$scope.close();
		});
  	};	
  
  	$scope.close = function(){
  		loadActivities($scope, $http);
    	dialog.close();
  	};
});

app.controller('ShowActivityCtrl', function($scope, $http, activity, dialog){


	$scope.close = function() {
		document.getElementsByTagName("body")[0].style.position = "static";
		dialog.close();
	}

	$scope.report = function(activity) {
		var getRequestReport = {
			method: 'GET',
			url: 'rest/activity/report/' + activity.id
		}
		$http(getRequestReport);
		document.getElementById("reportButton").style.display = "none";
		document.getElementById("reportMessage").style.display = "block";
	}

	$scope.reply = function()
	{

		console.log(activity);
		var postRequest = {
    	method : 'POST',
       	url: 'rest/comment' ,
       	data: {
  				activityID: activity.id,
					comment: document.getElementById("commentInput").value
			  }
		}

		$http(postRequest).then(function (response) {
  		})
	}

	

	$scope.prepareComments = function(data)
	{
		var result = [];
		for( var i = 0; i < data.length; i++ )
		{
			
			var date = new Date(data[i].postTime)
			var newDay = true;

			for( var j = 0; j < result.length; j++ )
			{
				if(result[j].timeOrder === "" + date.getFullYear() + date.getMonth() + date.getDate())
				{
					newDay = false;
					data[i].timeReadable = (date.getHours() < 10 ? '0' : '') + date.getHours()  + " : " + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
					result[j].comment.push(data[i]);
					result[j].comment.sort(function(a,b){return b.postTime - a.postTime;});
				}
			}
			if(newDay)
			{
				var day = {
					timeOrder:  "" + date.getFullYear() + date.getMonth() + date.getDate(),
					year: date.getFullYear(),
					month: date.getMonth(),
					date: date.getDate(),
					day: date.getDay(),
					timeReadable: days[date.getDay()] + " - " + date.getDate() + " " + months[date.getMonth()] + " " + date.getFullYear(),
					timeReadableAlt: days[date.getDay()] + " - " + date.getDate() + " " + months[date.getMonth()] + " " + date.getFullYear(),
					comment: [data[i]]
				}
				day.comment[0].timeReadable = (date.getHours() < 10 ? '0' : '') + date.getHours()  + " : " + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
				result.push(day);
				
			}
		}
		result.sort(function(a,b){return b.timeOrder - a.timeOrder;});
		return result;
	}
	

	
	var detailsRequest = {
		method: 'GET',
		url: 'rest/details/' + activity.id
	}

	$http(detailsRequest).then(function(response) {
		$scope.activity = response.data;

		var modal = document.getElementsByClassName("modal");
		modal[0].style.top = "0%";
		modal[0].style.left = "0%";
		modal[0].style.marginLeft = "0";
		modal[0].style.width = "100%";
		modal[0].style.height = "100%";
		modal[0].style.width = "100%";
		modal[0].style.backgroundColor = "transparent";
		modal[0].style.overflowY = "scroll";
		
		document.getElementsByTagName("body")[0].style.position = "fixed";

		window.addEventListener('click', function(e){   
  			if (!document.getElementById('showContainer').contains(e.target) || document.getElementById('showClose').contains(e.target)){
    			$scope.close();
  			}
		});
		
	});

	var detailsRequest = {
		method: 'GET',
		url: 'rest/comment/' + activity.id
	}
	$http(detailsRequest).then(function(response) {
		console.log(response);
		$scope.activity.days = $scope.prepareComments(response.data);
		console.log($scope.activity.days);
	});
	


});


app.controller('PostCtrl', function($scope, $http, dialog){

	var imgData = null;
	
	var emailIsValid = false;
	var ZIPIsValid = false;
	var imgValidSize = true;
	var imgValidType = true;
	
	var zipNotYetEntered = true;
	
	$scope.verifyPostForm = function() {
		var title = document.getElementById('postTitle').value;
		var text = document.getElementById('postText').value;
		var tags = document.getElementById('postTags').value;
		var university = document.querySelector('input[name = "postUniversity"]:checked').value;
		var faculty = document.getElementById('postFaculty').value;
		var email = document.getElementById('postEmail').value;
		
		/*console.log("changed");
		console.log(title);
		console.log(text);
		console.log(tags);
		console.log(university);
		console.log(faculty);
		console.log(email);*/
		
		if(title.length > 0 && text.length > 0 && tags.length > 0 && university.length > 0 && faculty.length > 0 && email.length > 0 && emailIsValid && ZIPIsValid) {
			document.getElementById("postSubmit").disabled = false;
			document.getElementById("postSubmit").classList.remove('buttonDisabled');
		} else {
			document.getElementById("postSubmit").disabled = true;
			document.getElementById("postSubmit").classList.add('buttonDisabled');
		}
	}
	
	$scope.checkUni = function() {
	
		var uni = document.querySelector('input[name = "postUniversity"]:checked').value;

		if(uni == "MUAS") {
			document.getElementById("postZIPtxtUS").style.display = "none";
			document.getElementById("postZIPtxtGerman").style.display = "block";
		} else if(uni == "CalPoly") {
			document.getElementById("postZIPtxtGerman").style.display = "none";
			document.getElementById("postZIPtxtUS").style.display = "block";
		}

		if(!zipNotYetEntered)
			$scope.checkZIP();
		
	}
	
	$scope.checkZIP = function() {

		var zip = document.getElementById('postZIPCode').value;
		var uni = document.querySelector('input[name = "postUniversity"]:checked').value;

		if(uni == "MUAS") {
			document.getElementById("ZIPHintUS").style.display = "none";
			if(zip.length == 5 && !isNaN(parseInt(zip)) && parseInt(zip).toString().length == 5) {
				ZIPIsValid = true;
				document.getElementById("ZIPHintGerman").style.display = "none";
			} else {
				ZIPIsValid = false;
				document.getElementById("ZIPHintGerman").style.display = "block";
			}
		} else if (uni == "CalPoly") {
			document.getElementById("ZIPHintGerman").style.display = "none";
			if(isNaN(parseInt(zip)) && zip.length > 0) {
				ZIPIsValid = true;
				document.getElementById("ZIPHintUS").style.display = "none";
			} else {
				ZIPIsValid = false;
				document.getElementById("ZIPHintUS").style.display = "block";
			}
		}
		
		
		zipNotYetEntered = false;
	}
	
	$scope.checkEmail = function() {

		var email = document.getElementById('postEmail').value;
		var hm = email.substring(email.length-7, email.length);
		var cpp = email.substring(email.length-8, email.length);

		if(hm == "@hm.edu" || cpp == "@cpp.edu") {
			emailIsValid = true;
			document.getElementById("mailHint").style.display = "none";
		} else {
			emailIsValid = false;
			document.getElementById("mailHint").style.display = "block";
		}
		
	}
	
	$scope.checkImageType = function(file) {

		var ext = file.name.split('.').pop().toLowerCase();
    	if(ext == "gif" || ext == "png" || ext == "jpg" || ext == "jpeg") {
        	imgValidType = true;
        	document.getElementById("imgTypeHint").style.display = "none";
   		} else {
   			imgValidType = false;
   			document.getElementById("imgTypeHint").style.display = "block";

			$scope.deleteImage();  	

   		}	
	}

	$scope.checkImageSize = function(file) {
    	var maxSize = 1024 * 1000 * 1;
    
    	if(file.size > maxSize) {
        	imgValidSize = false;
        	document.getElementById("imgSizeHint").style.display = "block";

			$scope.deleteImage();  	

    	} else {
    		imgValidSize = true;
    		document.getElementById("imgSizeHint").style.display = "none";
		}
		
	}
	
	$scope.deleteImage = function() {

        document.getElementById("postImg").value = "";
        var preview = document.querySelector('#postImgPreview');
   		preview.src = "";
   		document.getElementById("postImgPreview").style.display = "none";
		document.getElementById("postImgPreviewTxt").style.display = "none";
		document.getElementById("deleteImage").disabled = true;
		document.getElementById("deleteImage").classList.add('buttonDisabled');
	
		imgData = null;
	}

	$scope.previewImage = function() {
	
	
		var preview = document.querySelector('#postImgPreview');
		var file = document.querySelector('#postImg').files[0];
		var reader = new FileReader();
		
		$scope.checkImageType(file);
		$scope.checkImageSize(file);
		$scope.verifyPostForm();

		if(imgValidType && imgValidSize)
		{

  			reader.addEventListener("load", function () {
    			preview.src = reader.result;
    			imgData = reader.result;
    			//imgData = imgData.substring(imgData.indexOf(',')+1, imgData.length);
  			}, false);

  			if (file) {
    			reader.readAsDataURL(file);
			}
		
			document.getElementById("postImgPreview").style.display = "block";
			document.getElementById("postImgPreviewTxt").style.display = "block";
			
			document.getElementById("deleteImage").disabled = false;
			document.getElementById("deleteImage").classList.remove('buttonDisabled');
 
	
		}
	}

  	$scope.save = function(Activity) {
  		var postRequest = {
    	method : 'POST',
       	url: 'rest/post' ,
       	data: {
  				text: $scope.activity.text,
  				tags: $scope.activity.tags,
  				title: $scope.activity.title,
  				eMail: $scope.activity.eMail,
  				uni: $scope.activity.uni,
  				faculty: $scope.activity.faculty,
  				image: imgData,
  				zipcode: $scope.activity.ZIPCode
			  }
		}  
		
  		$http(postRequest).then(function (response) {
  		    $scope.activities = response.data;
  		}).then(function () {
  			imgData = null;
  			$scope.close();
  		});
  	};
  
  	$scope.close = function(){;
    	dialog.close(undefined);
  	};
});

$(document).ready(function () {
  $('.group').hide();
  $('#activities-table').show();
  $('#table-chooser').change(function () {
    $('.group').hide();
    $('#'+$(this).val()).show();
  })
});







adjustButton = function(textArea)
{
	if(textArea.value == "")
	{
		document.getElementById("commentSubmit").disabled = true;
		document.getElementById("commentSubmit").classList.add("buttonDisabled");

	}
	else
	{
		document.getElementById("commentSubmit").disabled = false;
		document.getElementById("commentSubmit").classList.remove('buttonDisabled');
	}
		
}

resizeComment = function(textArea)
{
	var oldHeight = textArea.style.height;
	textArea.style.height = "";
	var newHeight = Math.max(textArea.scrollHeight - 20, 60);
	textArea.style.height = oldHeight;
	textArea.style.height = newHeight + 'px';
}

focusComment = function(textArea)
{
	if(textArea.value === "")
	{
		textArea.placeholder = "";
		textArea.style.height = "60px";
		document.getElementById("commentSubmit").style.display = "block";
	}
}

unfocusComment = function(textArea)
{
	if(textArea.value === "")
	{
		
		textArea.placeholder = "Comment ...";
		textArea.style.height = "20px";
		document.getElementById("commentSubmit").style.display = "none";
	}
}






</script>
</head>
<body onload="angular.element(document.querySelector('#menu-container')).scope().getView();">

	<div ng-controller="ActivityCtrl">
	
	

	
		<div id="menu-container" ng-controller="MenuCtrl">
			<div id="logo-container"><img id="logo" src="logo_white.svg"></div>
			<div id="menu">
				<div class="menu-item" id="menu-item-map" onclick="angular.element(this).scope().showMap1()">Map MUAS</div>
				<div class="menu-item" id="menu-item-map2" onclick="angular.element(this).scope().showMap2()">Map CalPoly</div>
				<div class="menu-item current-menu-item" id="menu-item-activities-table" onclick="angular.element(this).scope().showList()">List</div>
				<div class="menu-item" ng-click="add()">Add Activity</div>
				
				<% if(request.getAttribute("login") != null && request.getAttribute("login").equals((Boolean)true)) { %>
				<div class="menu-item-admin">
					<img id="adminLogoMenu" src="user32.png" onclick="(document.getElementById('userBox').style.display == 'none') ? document.getElementById('userBox').style.display = 'block' : document.getElementById('userBox').style.display = 'none';"/>
					<div id="userBox" style="display:none">
						<strong>${admin.name}</strong><br/>
						<span  class="logoutButton" ng-click="logout()">Logout</span>
					</div>
				</div>
				<% } %>
			</div>


		</div>

		
		<div>
			<div id="map" class="map"></div>
			<div id="map2" class="map"></div>
		</div>
		
		<% //user is not admin %>
		<% if(!(request.getAttribute("login") != null && request.getAttribute("login").equals((Boolean)true))) { %>
		
		<!-- normal user view -->
		<div id="activities-table">
		<table class="mdl-data-table">

			<tr class="table-head">
				<td class="mdl-data-table__cell--non-numeric">Title</td>
				<td class="mdl-data-table__cell--non-numeric">University</td>
				<td class="mdl-data-table__cell--non-numeric">Tags</td>
				<td class="mdl-data-table__cell--non-numeric"></td>
			</tr>
			<tr ng-repeat="activity in activities">
				<td class="mdl-data-table__cell--non-numeric">{{activity.title}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{activity.uni}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{activity.tags}}</td>
				<td class="mdl-data-table__cell--non-numeric">
				<!-- Disabeld for sprint 1
					<button class="mdl-button" ng-click="edit(activity)">edit</button>
					<button class="mdl-button" ng-click="delete(activity)">delete</button> -->
					<button class="mdl-button" ng-click="show(activity)">show</button>
				</td>
			</tr>
		</table>
		</div>
		
		<% } %>
		<% //user is admin %>
		<% if(request.getAttribute("login") != null && request.getAttribute("login").equals((Boolean)true)) { %>
		
		<!-- admin view -->
		<select id="table-chooser">
		  <option value="activities-table">Normal View</option>
		  <option value="report-table-asc">Report Handling Ascending</option>
		  <option value="report-table-desc">Report Handling Descending</option>
		</select>
		
		
		
		<div id="activities-table" class="group">
		<table class="mdl-data-table">

			<tr class="table-head">
				<td class="mdl-data-table__cell--non-numeric">Title</td>
				<td class="mdl-data-table__cell--non-numeric">University</td>
				<td class="mdl-data-table__cell--non-numeric">Tags</td>
				<td class="mdl-data-table__cell--non-numeric"></td>
			</tr>
			<tr ng-repeat="activity in activities">
				<td class="mdl-data-table__cell--non-numeric">{{activity.title}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{activity.uni}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{activity.tags}}</td>
				<td class="mdl-data-table__cell--non-numeric">
				<!-- Disabeld for sprint 1
					<button class="mdl-button" ng-click="edit(activity)">edit</button>
					<button class="mdl-button" ng-click="delete(activity)">delete</button> -->
					<button class="mdl-button" ng-click="show(activity)">show</button>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="report-table-asc" class="group">
		<table class="mdl-data-table">

			<tr class="table-head">
				<td class="mdl-data-table__cell--non-numeric">Title</td>
				<td class="mdl-data-table__cell--non-numeric">University</td>
				<td class="mdl-data-table__cell--non-numeric">Tags</td>
				<td class="mdl-data-table__cell--non-numeric">Report#</td>
				<td class="mdl-data-table__cell--non-numeric"></td>
			</tr>
			<!-- | orderBy:'+':true reverses ng-repeat-->
			<tr ng-repeat="reportedActivity in reportedActivities">
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.title}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.uni}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.tags}}</td>
				<td class="mdl-data-table__cell--numeric">{{reportedActivity.reportCounter}}</td>
				<td class="mdl-data-table__cell--non-numeric">
				<!-- Disabeld for sprint 1
					<button class="mdl-button" ng-click="edit(activity)">edit</button> -->
					<button class="mdl-button" ng-click="delete(reportedActivity)">delete</button>
					<button class="mdl-button" ng-click="show(reportedActivity)">show</button>				
				</td>
			</tr>
		</table>
		</div>
		
		<div id="report-table-desc" class="group">
		<table class="mdl-data-table">

			<tr class="table-head">
				<td class="mdl-data-table__cell--non-numeric">Title</td>
				<td class="mdl-data-table__cell--non-numeric">University</td>
				<td class="mdl-data-table__cell--non-numeric">Tags</td>
				<td class="mdl-data-table__cell--non-numeric">Report#</td>
				<td class="mdl-data-table__cell--non-numeric"></td>
			</tr>
			<!-- | orderBy:'+':true reverses ng-repeat-->
			<tr ng-repeat="reportedActivity in reportedActivities | ordeloadActivitiesrBy:'+':true">
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.title}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.uni}}</td>
				<td class="mdl-data-table__cell--non-numeric">{{reportedActivity.tags}}</td>
				<td class="mdl-data-table__cell--numeric">{{reportedActivity.reportCounter}}</td>
				<td class="mdl-data-table__cell--non-numeric">
				<!-- Disabeld for sprint 2
					<button class="mdl-button" ng-click="edit(activity)">edit</button> -->
					<button class="mdl-button" ng-click="delete(reportedActivity)">delete</button>
					<button class="mdl-button" ng-click="show(reportedActivity)">show</button>				
				</td>
			</tr>
		</table>
		</div>
		
		<% } %>
		
	</div>
	
</body>
</html>