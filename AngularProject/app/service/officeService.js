const office = angular.module('office', [])
office.factory('officeService',['$http','$q','$window', function($http,  $q, $window){
    return{
        become: function(office){
            const becomeRequest ={
                token: localStorage.getItem('JwtToken'),
                office:office
            }
            $http.post('http://localhost:8080/become',becomeRequest).
            then(function (response) {
                console.log(response)
                if(response.status == 400){
                    alert("something wrong!")
                }
                else{
                    $window.location.reload()
                }
            })
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

                if(response.status == 400){
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