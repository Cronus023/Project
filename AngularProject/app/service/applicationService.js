const application = angular.module('application', [])
application.factory('applicationService', ['$http', '$q', '$window', function ($http, $q, $window) {
    return {
        createApplication: function (application, reasons, office) {
            const deferred = $q.defer()
            const requestBody = {
                application: {
                    additionalInfo: application.additionalInfo,
                    educationalProgram: application.educationalProgram,
                    groups: application.groups,
                    reasons: reasons
                },
                office: office
            }
            $http.post('http://localhost:8080/application/createApplication', requestBody).then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getApplications: function () {
            const deferred = $q.defer()
            $http.get('http://localhost:8080/application/getApplications').then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getProviderApplications: function () {
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/application/getProviderApplications?login=${login}`).then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },

        getEducationalProgramById: function (id) {
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/application/getEducationalProgramById?id=${id}&login=${login}`).then(function success(response) {
                if (response.data.title) {
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getApplication: function (id) {
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/application/getApplication?id=${id}&login=${login}`).then(function success(response) {
                if (response.data.title) {
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getHistory: function (id) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/application/getHistory?id=${id}`).then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        finalDecision: function (id, decision) {
            const deferred = $q.defer()
            $http.post(`http://localhost:8080/application/finalDecision?id=${id}&decision=${decision}`).then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        rejectAndAccept: function (id, application, status, section) {
            const deferred = $q.defer()
            const requestBody = {
                application: application,
                login: localStorage.getItem('UserLogin'),
                status: status,
                typeOfSection: section
            }
            $http.post(`http://localhost:8080/application/rejectAndAccept`, requestBody).then(function success(response) {
                if (response.data.title !== 'ok!') {
                    alert(response.data.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                deferred.resolve(response.data)
            })
            return deferred.promise
        }
    }
}])