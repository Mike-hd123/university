using System.Linq;
using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using GrpcWebBlazorWasm.Server.Data;
using GrpcWebBlazorWasm.Shared;

namespace GrpcWebBlazorWasm.Server.Services

{
    public class TouristRouteSevice :
        TouristRouteProtoSevice.TouristRouteProtoSeviceBase
    {
        private readonly ApplicationDbContext _dbContext;

        public TouristRouteSevice(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public override async Task<TouristRoute> GetTouristRoute(TouristRouteId request,
            ServerCallContext context)
        {
            var touristRoute =
                await _dbContext.TouristRoutes.SingleOrDefaultAsync(route => route.Id == request.Id);

            //touristRoute.Price = touristRoute.OriginalPrice *(decimal) (touristRoute.DiscountPresent??1);  
            //
            touristRoute.TouristRouteAppend();

            return touristRoute;
        }

        public override async Task<TouristRoutes> GetTouristRoutes(Empty request, ServerCallContext context)
        {
            TouristRoutes touristRoutes = new();
            var touristRoutesFrom = await _dbContext.TouristRoutes.ToListAsync();

            //touristRoutesFrom.ForEach(touristRoute => touristRoute.Price = touristRoute.OriginalPrice * (decimal)(touristRoute.DiscountPresent ?? 1));   
            touristRoutesFrom.ForEach(touristRoute => touristRoute.TouristRouteAppend());

            touristRoutes.TouristRouteList.AddRange(touristRoutesFrom);

            return touristRoutes;
        }
        public override async Task<TouristRoutes> SearchTouristRoute(TouristRouteName request, ServerCallContext context)
        {
            TouristRoutes touristRoutes = new();

            var touristRoutesFrom = await _dbContext.TouristRoutes.AsQueryable().Where(route => EF.Functions.Like(route.Title, $"%{request.Name}%")).ToListAsync();

            //touristRoutesFrom.ForEach(touristRoute => touristRoute.Price = touristRoute.OriginalPrice * (decimal)(touristRoute.DiscountPresent ?? 1));   
            touristRoutesFrom.ForEach(touristRoute => touristRoute.TouristRouteAppend());

            touristRoutes.TouristRouteList.AddRange(touristRoutesFrom);

            return touristRoutes;
        }

        public override async Task<Count> DelTouristRoute(TouristRoute TouristRoute,
            ServerCallContext context)
        {
            _dbContext.TouristRoutes.Remove(TouristRoute);
            int size = _dbContext.SaveChanges();
            return new Count
            {
                Size = size
            };
        }

        public override async Task<Count> EdiTouristRoute(TouristRoute TouristRoute,
            ServerCallContext context)
        {
            TouristRoute.OriginalPrice = TouristRoute.Price;
            _dbContext.TouristRoutes.Update(TouristRoute);
            int size = _dbContext.SaveChanges();
            return new Count
            {
                Size = size
            };
        }

        public override async Task<TouristRouteId> AddTouristRoute(TouristRoute TouristRoute,
            ServerCallContext context)
        {
            TouristRoute.Id = Guid.NewGuid().ToString();
            TouristRoute.CreateTime = DateTime.UtcNow.ToTimestamp();
            TouristRoute.OriginalPrice = TouristRoute.Price;
            _dbContext.TouristRoutes.Add(TouristRoute);
            _dbContext.SaveChanges();
            return new TouristRouteId
            {
                Id = TouristRoute.Id
            };
        }
    }
}
