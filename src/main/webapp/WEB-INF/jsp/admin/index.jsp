<!DOCTYPE html>
<html>
	<head>
		<title>ActiviTracker</title>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>

		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.1.0/ui-bootstrap-tpls.js"></script>
			
		<link rel="shortcut icon" type="image/png" href="../favicon.png">

		<link rel="stylesheet" type="text/css" href="../style.css" />


		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
		<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

		<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet"> 
	</head>
	<body ng-app="LoginApp">
		<div id="menu-container" >
			<div id="logo-container"><img id="logo" src="../logo_white.svg"></div>
			<div id="menu">
				<div class="menu-item" id="menu-item-map" onclick="window.location.href='../?map=muas';">Map MUAS (sprint2)</div>
				<div class="menu-item" id="menu-item-map2"onclick="window.location.href='../?map=calpoly';">Map CalPoly (sprint2)</div>
				<div class="menu-item" id="menu-item-activities-table" onclick="window.location.href='../';">List</div>
			</div>
		</div>
		
		<div class="loginBox" ng-controller="LoginCtrl">
			<div id="messageBox">
					
			</div>
			<div class="loginLabel">Admin</div>
			<form>
			<div class="eMailDiv"><input name="email" id="loginEmail"  placeholder="Username" onfocus="this.placeholder=''" onfocusout="this.placeholder='Username'" ng-model="user.name" ng-init="user.name=''"/></div>
				<div class="passDiv"><input type="password" name="pass" placeholder="Password" id="loginPass" onfocus="this.placeholder=''" onfocusout="this.placeholder='Password'"  ng-model="user.password" ng-init="user.password=''"/></div>
				<div class="buttonDiv"><button id="loginButton" class="loginButton" ng-click="login(user)">Login</button></div>
			</form>
			<div class="loginAdditional">
				<label class="remember loginFooter" for="remember">
					<input type="checkbox" class="checkBox" name="remember" id="remember"  ng-model="user.forever" ng-init="user.forever=false">
					Remember Me
				</label>
				<label class="forgotPass loginFooter" onclick="alert('Your problem');">
					Forgot password?
				</label>
			</div>
		</div>

		<script>
			var app = angular.module('LoginApp', []);
			app.controller('LoginCtrl', function($scope, $http)
			{
				$scope.login = function(user) 
				{
					if(user.name == "")
					{
						messageBox = document.getElementById("messageBox");
						messageBox.style.display = "block";
						messageBox.style.bottom = "70px";
						messageBox.className = "errorBorder";
						messageBox.innerHTML = "Please enter a username";
					}
					else if(user.password == "")
					{
						messageBox = document.getElementById("messageBox");
						messageBox.style.display = "block";
						messageBox.style.bottom = "70px";
						messageBox.className = "errorBorder";
						messageBox.innerHTML = "Please enter a password";
					}
					else
					{

						
						var postRequest = 
						{
							method : 'POST',
							url: 'rest/admin' ,
							data: user
						}  
			
						$http(postRequest).then(function (response) {
							if( response.data == true )
							{
								window.location.href='../';
							}
							else
							{
								messageBox = document.getElementById("messageBox");
								messageBox.style.display = "block";
								messageBox.style.bottom = "90px";
								messageBox.className = "errorBorder";
								messageBox.innerHTML = "Invalid username or password.<br/>Please try again.";
							}
						});
					}
				}
				
			});
	
		</script>
	</body>
</html>