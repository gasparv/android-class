using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace TestApi2.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MovieController : ControllerBase
    {
        private MovieContext _db;
        public MovieController(MovieContext db)
        {
            _db = db;
        }


        [HttpGet]
        public List<Movie> getAllMovies()
        {
            return _db.Movie.ToList();
        }

        [HttpPost]
        public IActionResult postMovie([FromForm]Movie movie)
        {
            _db.Movie.Add(movie);
            try
            {
                _db.SaveChanges();
                return Ok();
            }
            catch(Exception e)
            {
                return BadRequest();
            }
        }

    }
}