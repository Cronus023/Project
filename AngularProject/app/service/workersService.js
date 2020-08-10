const worker = angular.module('workers', [])
worker.factory('workersService',['$http','$q','$window', function($http,  $q, $window){
    return{
        delete: function(){
            $http.post('http://localhost:8080/workers/delete').
            then(function (response) {
                console.log(response)
            })
        },
        add: function(worker, officeId){
            const requestBody = {
                worker: worker,
                officeId:officeId
            }
            $http.post('http://localhost:8080/workers/add', requestBody).
            then(function (response) {
                console.log(response)
            })
        },
        edit: function(){
            $http.post('http://localhost:8080/workers/edit').
            then(function (response) {
                console.log(response)
            })
        }
    }
}] )