'use strict';

describe('myApp.office module', function() {

    beforeEach(module('myApp.office'));

    describe('office controller', function(){

        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('OfficeCtrl');
            expect(LoginCtrl).toBeDefined();
        }));

    });
});