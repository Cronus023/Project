const worker = angular.module('workers', [])
worker.factory('workersService',['$http','$q','$window', function($http,  $q, $window){
    return{
        get_workers: function(id){
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/workers/get_workers?name=${id}`).
            then(function (response) {
                if(response.status !=400){
                    deferred.resolve(response.data)
                }
                else alert("Error!")
            })
            return deferred.promise
        },
        delete: function(){
            $http.post('http://localhost:8080/workers/delete').
            then(function (response) {
                console.log(response)
            })
        },
        add: function(worker, officeId){
            const requestBody = {
                worker: worker,
                officeName:officeId
            }
            console.log(requestBody)
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