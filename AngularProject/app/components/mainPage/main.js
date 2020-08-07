'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, $http, $window) {

        $http.get('http://localhost:8080/get_office').
        then(function (response) {
            $scope.offices = response.data
        })

        $scope.become = function(office){
             const becomeRequest ={
                 token: localStorage.getItem('JwtToken'),
                 office:office
             }
             $http.post('http://localhost:8080/become',becomeRequest).
             then(function (response) {
                 if(response.status == 400){
                     alert("something wrong!")
                 }
                 else{
                     $window.location.reload()
                 }
             })
        }

    });