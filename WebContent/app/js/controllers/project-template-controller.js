/**
 * Since Geppetto.v1.0
 * Created:  01/11/2016.
 * @author Dheeraj Singh
 *
 * Project Template Controller
 *
 */

app.controller('ProjectTemplateCtrl',
  ['$log', '$scope', '$routeParams', '$http', '$timeout', '$location', '$cookieStore', 'RestURL', '$modal', 'ActivityInformation','UserDetailService',
    function ($log, $scope, $routeParams, $http, $timeout, $location, $cookieStore, RestURL, $modal, ActivityInfo,UserDetailService) {

      var self = $scope;

      /**
       * used to hold the template list
       */
      self.templates = null;

      /**
       * used to hold the project template
       */
      self.projectTemplate = {
        templateComponents: []
      };
      self.organizationOfUser = {
    		  organization_name:''
      }
      self.currentProjectTemplate = null;

      /**
       * initializing project template controller
       */
      self.init = function () {
        $log.debug("Project Template Controller has been initialized!");
        $log.log('Project Id: ', ActivityInfo.projectId);
        $log.log('Project Template Id: ', ActivityInfo.activityId);
        $log.log('Project Template Device: ', ActivityInfo.deviceTypes);

        console.log(" ----- >  ",angular.toJson(UserDetailService.organizationForLoggedUser));
        self.organizationOfUser.organization_name = UserDetailService.organizationForLoggedUser.organization_name;
        
        /* initialize the project id */
        self.projectTemplate.projectId = ActivityInfo.projectId;

        /* initialize the project template id if not null */
        if (ActivityInfo.activityId != null) {
          self.projectTemplate.projectTemplateId = ActivityInfo.activityId;
        }

        /* initialize the device if not null */
        if (ActivityInfo.deviceTypes != null) {
          self.projectTemplate.device = ActivityInfo.deviceTypes;
        }

        /**
         * initializing
         */
        self.getTemplates();
        self.getProjectTemplate();//dhina
      };

      /**
       * loading all templates
       */
      self.getTemplates = function () {

        $http.get(RestURL.baseURL + 'template/get_all_template/?organization_id='+UserDetailService.organizationForLoggedUser.id)
          .success(function (response) {
            self.templates = response;
            $log.debug('Project Template Info',JSON.stringify(response));

          }).error(function (response) {
          $log.debug("unable to get the project templates!");
        });
      };

      /**
       * loading all templates
       */
      self.getTemplateComponents = function () {
        if (self.currentProjectTemplate != null
          && self.currentProjectTemplate.projectTemplateId != null
          && self.currentProjectTemplate.projectTemplateId != 0
          && self.currentProjectTemplate.templateId != null
          && self.currentProjectTemplate.templateId != 0
          && self.currentProjectTemplate.templateId == self.projectTemplate.templateId) {
          // get template components
          $http.get(RestURL.baseURL + 'projecttemplate/get_component_by_project_template_and_template/'
              + '?templateId=' + self.currentProjectTemplate.templateId
              + "&projectTemplateId=" + self.currentProjectTemplate.projectTemplateId)
            .success(function (response) {
              self.projectTemplate.templateComponents = response;
            //  console.error('Project Template Components for template: ' + self.projectTemplate.templateId, JSON.stringify(response));

            }).error(function (response) {
            $log.debug('unable to get the project template Components for template:' + self.projectTemplate.templateId);
          });
        } else {
          // get template components
          $http.get(RestURL.baseURL + 'template/get_template_component_by_template/?templateId=' + self.projectTemplate.templateId)
            .success(function (response) {
              self.projectTemplate.templateComponents = response;
              $log.debug('Project Template Components for template: with id ' + self.projectTemplate.templateId, JSON.stringify(response));

            }).error(function (response) {
            $log.debug('unable to get the project template Components for template: ' + self.projectTemplate.templateId);
          });
        }
      };

      /**
       * loading project template
       */
      self.getProjectTemplate = function () {
        if (self.projectTemplate.projectTemplateId == null || self.projectTemplate.projectTemplateId == 0) {
          return;
        }
        $http.get(RestURL.baseURL + 'projecttemplate/get/?id=' + self.projectTemplate.projectTemplateId)
          .success(function (response) {
            self.projectTemplate = response;
            self.currentProjectTemplate = response;
            $log.debug('Project Template Info getProjectTemplate -  > ',JSON.stringify(response));

            // load template components  
            self.getTemplateComponents();// sasi

          }).error(function (response) {
          $log.debug("unable to get the project template!");
        });
      };

      /**
       * cancel button handler on the project template designer screen
       */
      self.cancelProjectTemplate = function () {
        $location.url('/en-US/project/update/');
      };

      /**
       * delete button handler for the project template designer screen
       */
      self.deleteProjectTemplate = function () {
        $http.get(RestURL.baseURL + 'projecttemplate/delete/?id=' + self.projectTemplate.projectTemplateId)
          .success(function (response) {
            $location.url('/en-US/project/update/');
          }).error(function (response) {
          $log.debug('unable to delete the project template!', response);
        });
      };

      /**
       * update button handler for the project template screen
       */
      self.updateProjectTemplate = function () {
        // $log.log('*********** self.projectTemplate : ' + JSON.stringify( self.projectTemplate ) );
        $http({
          url: RestURL.baseURL + 'projecttemplate/' + ( (self.projectTemplate.projectTemplateId == null || self.projectTemplate.projectTemplateId == 0) ? 'create/' : 'update/' ),
          data: angular.toJson(self.projectTemplate),
          method: 'POST',
          headers: {
            'Accept': 'application/json'
          }
        }).success(function (response) {
          $cookieStore.remove('back');
         // $location.url('/en-US/projecttemplate/update/');
        	$location.url('/en-US/project/update/');
        }).error(function (response) {
          $log.debug('unable to update the project template!', response);
        });
      };

      /**
       * triggering init
       */
      self.init();

    }]);