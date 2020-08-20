const office = angular.module('office', [])
office.factory('officeService',['$http','$q','$window', function($http,  $q, $window){
    return{
        become: function(office){
            const becomeRequest ={
                login: localStorage.getItem('UserLogin'),
                office:office
            }
            $http.post('http://localhost:8080/become',becomeRequest).
            then(function (response) {
                $window.location.reload()
            })
        },
        get_office_by_provider_login: function(login){
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/get_office_by_login?login=${login}`).
            then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_office_by_name: function(name){
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/get_office_by_name?name=${name}`).
            then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_office: function(){
            const deferred = $q.defer()
            $http.get('http://localhost:8080/get_office').
            then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        create: function(officeBody){
            const deferred = $q.defer()

            $http.post('http://localhost:8080/create',officeBody).
            then(function (response) {
                if(response.status === 400){
                    deferred.resolve(response.data)
                }
                else{
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
            })
            return deferred.promise
        }
    }
}] )