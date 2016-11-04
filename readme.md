<div >

<h2>
Vagtmanager 2.0
</h2>
<uib-accordion-group>
<uib-accordion-heading>
        Decription
      </uib-accordion-heading>
      <h3>Description</h3>
      <p>This project is a project for Røde Kors Samaritterne in Denmark.</p>
      <p>The goal is that this project should be used to organize activities for the diffrent departments  </p>
</uib-accordion-group >

    <uib-accordion-group is-open="status.isFirstOpen" >
      <uib-accordion-heading>
        Architecture
      </uib-accordion-heading>
      <h3> Architecture</h3>
      <p>This Version of the Seed provides a Java JAX RS Backend and an Angular Frontend in a single Maven Web Project</p>

      <div class="col-md-8">
        <ul>
          <li>All Backend Code should be placed in the <em>Source Packages</em> Folder</li>
          <li>All Client Code should be placed in the <em>Web Pages</em> Folder (in the sub-folder <b>app</b>) which holds the Angular Frontend Project</li>
        </ul>
      </div>
      <div class="col-md-4">
      </div>
    </uib-accordion-group>

    <uib-accordion-group >
      <uib-accordion-heading>

        Backend Architecture
      </uib-accordion-heading>
      <br/>
    </uib-accordion-group>

    <uib-accordion-group >
      <uib-accordion-heading>       
        Frontend Architecture
      </uib-accordion-heading>
      <div class ="col-md-9">
        <p>The seed is built from <a href="https://github.com/angular/angular-seed">AngularJSs</a> seed and the application skeleton of the seed is therefore heavily inspired by this seed.</p>
        <p>This seed is "twisted" to make it easier to use in a school, see this <a href="https://scotch.io/tutorials/angularjs-best-practices-directory-structure">link</a> for alternative seed-structures.</p> 
        <p>All the content of the Site Root (an alias for the app-folder) is what makes up the web-application and is what will be copied to the "Web Pages" folder of the Server project whenever you perform a clean build or deploy this project.</p>
        <ol class="list">
          <li> Contains all dependencies for the web-application (Angular-files, etc.)  </li>
          <li> A folder with files for "global" filters, directives, factories and services. If you place code in one of these files you don't need do any "includes" but obviously things must still be injected to use them in your code </li>
          <li> All the Views for this application. Each folder must as a minimum include a template for the view and a routeprovider with the routing-details </li>
          <li> The app.js (your Java-main counterpart). Open the file to understand it's purpose</li>
          <li> index.html holds the top-menu and the <em>ng-view directive</em>. All external files are included here</li>
          <li>This is where you should place all Karma-unit tests for the project</li>
          <li>Configuration-files to setup all dependencies</li>
          <li>Right-click this icon to setup Karma defaults </li>
        </ol>

      </div>
    </uib-accordion-group>

    <uib-accordion-group heading="Security Features in the Seed" >
      <h3>Test Users: (logon with these credentials to test authentication)</h3>
      <p>The Backend supports whatever roles you come up with. Out of the box the front-end only handles the roles "User" and "Admin". Adding more is however simple</p>
      <ul style="font-size: large">
        <li><b style="display: inline-block;width:7em;">User</b>username = user, password = test</li>
        <li><b style="display: inline-block;width:7em;">Admin:</b>username = admin, password = test</li>
        <li><b style="display: inline-block;width:7em;">User+Admin:</b>username = user_admin, password = test</li>
      </ul>
      <h3>Using the Security Features in this seed - <em>Server:</em></h3>
      <p>Decorate your REST classes and or methods with theese annotations: <em>@PermitAll</em> <em>@DenyAll</em> <em>@RolesAllowed</em>
      <p>See the section Securing RESTful Web Services Using Annotations here: <a href="http://docs.oracle.com/cd/E24329_01/web.1211/e24983/secure.htm#RESTF256">Securing RESTful Web Services</a>
      <P>See The server package <b>rest</b> provides examples of services using both the <em>User</em> and <em>Admin</em> Roles</p>
      <p>Username, password, and roles are hardcoded in UserFacade.java and the User Class (your task is to place this info in a database)</p>

      <h3>Using the Security Features in this seed - <em>Client</em>:</h3>
      <p>Logon/logof is ready to use.</p>
      <p>A token with credentials is automatically attached to all outgoing AJAX (REST)requests (when you are logged on) </p>
      <p>In this version users can see all menu items (even if they do not have the necessary rights). This is for you to
        observe when and where security takes place (bring up F12 in chrome and monitor the network traffic)<p>
      <p>You can hide elements (menu-items) so they are only visible when logged on with the required security role. Add <code>ng-show="isUser"</code> to
        the "view2" anchor tag in index.html and <code>ng-show="isAdmin"</code> on the "view2" tag. To see this in action.</p>
      <p>Everything you do, that relates to security on the client, can be tampered with, so the trick above should be seen as
        something to make the system more user friendly. <span style="text-decoration: underline">The real security is how REST (our data) is protected on the server. </span>
      </p>
      <h4>Not sending the JWT for specific requests</h4>
      <p>In some cases (for example when doing Cross Origin Calls) we don not want to include the jwt-token with the request</p>
      <p>Use the skipAutherization property as sketched below, to prevent the Token from being include with a request</p>
      <pre>
        $http({
          url: '/hola',
          <b>skipAuthorization: true</b>
          method: 'GET'
        });</pre>
      <p>Security in this seed was inspired by these articles</p>
      <ul style="list-style: none">
        <li> <a href="https://auth0.com/blog/2014/01/07/angularjs-authentication-with-cookies-vs-token/">Cookies vs Tokens. Getting auth right with Angular.JS</a></li>
        <li><a href="http://scytl.github.io/restguide/#_security_2">Security</a></li>
      </ul>
    </uib-accordion-group>
</div>
