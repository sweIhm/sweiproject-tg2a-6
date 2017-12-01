  var geocoder;
  var map;

  var centerData;

  function initialize() {
      //var latlng = new google.maps.LatLng(-34.397, 150.644);
      var mapOptions = {
          zoom: 8,
          center: centerData
      }
      map = new google.maps.Map(document.getElementById('map'), mapOptions);
  }

  function codeAddress() {

      geocoder = new google.maps.Geocoder();


      var address = "80331";




      geocoder.geocode({
          'address': address
      }, function(results, status) {
          if (status == 'OK') {

              centerData = results[0].geometry.location;

          } else {
              alert('Geocode was not successful for the following reason: ' + status);
          }
      });


      initialize();

      geocoder.geocode({
          'address': address
      }, function(results, status) {
          if (status == 'OK') {
              map.setCenter(results[0].geometry.location);
              var marker = new google.maps.Marker({
                  map: map,
                  position: results[0].geometry.location
              });
              
                marker.addListener('click', function() {
					console.log(angular.element(this).scope().activities);
  				});
              
          } else {
              alert('Geocode was not successful for the following reason: ' + status);
          }
      });
  }
  

  