'use strict';

/* Place your global Factory-service in this file */

var app = angular.module('myApp.factories', []);

  app.factory('InfoFactory', function () {
    var info = "Hello World from a Factory";
    var getInfo = function getInfo(){
      return info;
    };
    return {
      getInfo: getInfo
    };
  });
  
  app.factory('UserFactory', function(){
     
      var department = "None";
      var createdUser = {};
      
      return{
          getDepartment : function(){
              return department;
          },
          setDepartment : function(input){
              department = input;
          },
          getCreatedUser : function(){
              return createdUser;
          },
          setCreatedUser : function(input){
              createdUser.userName = input;
          },
          getUserName : function(){
              return createdUser.userName;
          }
      };
  });