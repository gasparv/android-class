using JetBrains.Annotations;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TestApi2
{
    public class MovieContext : DbContext
    {
        public DbSet<Movie> Movie { get; set; }

        public MovieContext( DbContextOptions options) : base(options)
        {
        }
    }
}
