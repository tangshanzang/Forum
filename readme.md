This is project for security course.

DB structure and user authorities:
https://drive.google.com/file/d/1Hxd7I3UAu5DreE7xZgAV02_5URRCvlHL/view?usp=sharing

Tools used:
Java Spring, Vue, MySQL

Setup:
1. Create a schema named "forum" in MySQL
2. Change application.properties to your info
3. Run ForumApplication in backend.
4. In Frontend Terminal, type npm i, then npm run dev

About:
In this forum project any visitor can view Group, Thread and Post, but they do not have the authorities to create, edit and delete data.

In this project, upon successfully login, JWT will be send and stored as access_token and refresh_token. All api calls except for GET, will send access_token as header. If access_token is invalid/expired/empty, application will send refresh_token to try to get new tokens, and then retry that api call.

Used owasp to santitize inputs and passwords are encrypted. The current structure should be able to prvent XSS and injection attacks.

Deleted items will be displayed as [Deleted User/Group...], while their status attribute will be changed to [Deleted] to keep track in our DB. As reference to other forums, upon user delete, their group, thread and post will persist instead of also getting deleted.

If Item is Deleted or Blocked, it will no longer be able to be updated, deleted items can not be blocked, and vice versa.

Admin authorities only work on Admin page, if logged in user is admin, admin would appear on navbar, or visit /admin manually.

Roles:
Logged in users have two roles:
1. ROLE_USER: This is the default role given to all users, this group can create Group Thread and Post, as well as edit/delete their own data.
2. ROLE_ADMIN: This rolse can only be assigned directly in DB, this group has all authorities as ROLE_USER, and they can block, unblock and delete other's data.
