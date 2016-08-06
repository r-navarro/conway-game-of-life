'use strict';

angular.module('gol', [
  'ngRoute',
  'gol.grid'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $routeProvider.otherwise({redirectTo: '/gol'});
}]);