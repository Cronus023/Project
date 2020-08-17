'use strict';
describe('myApp.application.reviewers.regular module', function() {
    beforeEach(module('myApp.application.reviewers.regular'))
    describe('application.reviewers.regular controller', function(){
        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('RegularReviewersApplicationCtrl')
            expect(LoginCtrl).toBeDefined()
        }))
    })
})