using System;
using System.Linq;
using System.Threading.Tasks;
using GrpcWebBlazorWasm.Server.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.DependencyInjection;

namespace GrpcWebBlazorWasm.Server.Data
{
    public class SeedUsersAndRolesData
    {
        private readonly ApplicationDbContext ctx;
        public SeedUsersAndRolesData(ApplicationDbContext dbContext)
        {
            ctx = dbContext;
        }

        public async Task CreateUsersAndRoles(IServiceProvider serviceProvider)
        {
            if (ctx.Users.Any())
            {
                return;
            }

            // Initializing Custom Roles
            var RoleManager = serviceProvider.GetRequiredService<RoleManager<IdentityRole>>();
            var UserManager = serviceProvider.GetRequiredService<UserManager<ApplicationUser>>();
            string[] roleNames = { "Administrators", "Users" };
            IdentityResult roleResult;

            foreach (var roleName in roleNames)
            {
                var roleExist = await RoleManager.RoleExistsAsync(roleName);
                if (!roleExist)
                {
                    roleResult = await RoleManager.CreateAsync(new IdentityRole(roleName));
                }
            }

            // Create Administrator
            ApplicationUser admin = await UserManager.FindByEmailAsync("admin@qq.com");
            if (admin == null)
            {
                admin = new ApplicationUser()
                {
                    UserName = "admin@qq.com",
                    Email = "admin@qq.com",
                    CustomClaim = "AdminClaim"
                };
                await UserManager.CreateAsync(admin, "Aa31415926#");
            }
            // Add Roles
            await UserManager.AddToRoleAsync(admin, "Administrators");
            await UserManager.AddToRoleAsync(admin, "Users");

            // Create User
            ApplicationUser user = await UserManager.FindByEmailAsync("user@qq.com");

            if (user == null)
            {
                user = new ApplicationUser()
                {
                    UserName = "user@qq.com",
                    Email = "user@qq.com",
                    CustomClaim = "UserClaim"

                };
                await UserManager.CreateAsync(user, "Aa31415926#");
            }
            await UserManager.AddToRoleAsync(user, "Users");
        }

    }
}