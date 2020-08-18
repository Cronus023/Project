const application = angular.module('application', [])
application.factory('applicationService',['$http','$q','$window', function($http,  $q, $window){
    return{
        create_application: function(application, reasons,office){
            const deferred = $q.defer()
            const requestBody ={
                additionalInfo: application.additionalInfo,
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
        },
        get_applications: function(){
            const deferred = $q.defer()
            $http.get('http://localhost:8080/application/get_applications').
            then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_educational_program_by_id: function(id){
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/application/get_educational_program_by_id?id=${id}&login=${login}`).
            then(function success(response) {
                if(response.data.title){
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_application_by_id: function(id){
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/application/get_application?id=${id}&login=${login}`).
            then(function success(response) {
                if(response.data.title){
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_history_of_application: function(id){
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/application/get_history?id=${id}`).
            then(function success(response) {
                if(response.data.title){
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        reject_accept_application: function(id, application, status, section){
            const deferred = $q.defer()
            const requestBody = {
                application: application,
                login: localStorage.getItem('UserLogin'),
                status: status,
                typeOfSection: section
            }
            $http.post(`http://localhost:8080/application/reject_accept`, requestBody).
            then(function success(response) {
                if(response.data.title !== 'ok!'){
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        }
    }
}] )