using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GrpcWebBlazorWasm.Shared
{
    public partial class DecimalValue
    {
        private const decimal NanoFactor = 1_000_000_000;
        public DecimalValue(long units, int nanos)
        {
            Units = units;
            Nanos = nanos;
        }

        //public long Units { get; }
        //public int Nanos { get; }

        public static implicit operator decimal(DecimalValue grpcDecimal)
        {
            return grpcDecimal.Units + grpcDecimal.Nanos / NanoFactor;
        }

        public static implicit operator DecimalValue(decimal value)
        {
            var units = decimal.ToInt64(value);
            var nanos = decimal.ToInt32((value - units) * NanoFactor);
            return new DecimalValue(units, nanos);
        }

        public String ToString(String a)
        {
            Console.WriteLine(a);
            return ((decimal)this).ToString();
        }
    }
}
