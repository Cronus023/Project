'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, $http, $window) {
        $scope.flag = false
        $http.get('http://localhost:8080/get_office').
        then(function (response) {
            console.log(response)
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
        $scope.check = function(office){
            let flag = false
            if(office.leaderID.length == 0){
                return false
            }

            if(office.leaderID.length != 0){
                office.leaderID.map(item =>{
                    if(item.login == localStorage.getItem('UserLogin')){
                        flag = true
                    }
                })
            }
            return flag
        }


    });