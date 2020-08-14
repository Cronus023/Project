const application = angular.module('application', [])
application.factory('applicationService',['$http','$q','$window', function($http,  $q, $window){
    return{
        create_application: function(application, reasons,office){
            const deferred = $q.defer()
            const requestBody ={
                educationalProgram: application.educationalProgram,
                groups: application.groups,
                office:office,
                reasons: reasons
            }
            $http.post('http://localhost:8080/application/create', requestBody).
            then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        }
    }
}] )