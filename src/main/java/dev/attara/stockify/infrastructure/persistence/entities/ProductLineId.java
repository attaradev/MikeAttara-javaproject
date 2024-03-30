package dev.attara.stockify.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a composite primary key for the ProductLineEntity class.
 * Consists of orderId and productId.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductLineId implements Serializable {
    private String orderId;
    private String productId;

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare this ProductLineId against
     * @return True if the given object represents a ProductLineId equivalent to this ProductLineId, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductLineId that = (ProductLineId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);
    }

    /**
     * Returns a hash code value for the ProductLineId. This method is supported for the benefit of hash tables
     * such as those provided by HashMap.
     *
     * @return A hash code value for this ProductLineId
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
