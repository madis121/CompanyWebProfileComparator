var cwpcApp = angular.module('cwpc', ['cwpc.controllers', 'cwpc.services']);

cwpcApp.constant('CONSTANTS', {
	
});

//cwpcApp.directive('tooltip', function() {
//	return function(scope, element, attr) {
//		element.attr('data-toggle', 'tooltip');
//		element.attr('title', 'text');
//		scope.$watch('tooltip', function(nVal) {
//			console.log(nVal);
//		});
//	};
//});